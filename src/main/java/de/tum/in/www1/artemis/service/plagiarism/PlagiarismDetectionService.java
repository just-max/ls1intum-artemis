package de.tum.in.www1.artemis.service.plagiarism;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.jplag.exceptions.ExitException;
import de.tum.in.www1.artemis.domain.ProgrammingExercise;
import de.tum.in.www1.artemis.domain.TextExercise;
import de.tum.in.www1.artemis.domain.modeling.ModelingExercise;
import de.tum.in.www1.artemis.domain.plagiarism.PlagiarismDetectionConfig;
import de.tum.in.www1.artemis.domain.plagiarism.PlagiarismResult;
import de.tum.in.www1.artemis.domain.plagiarism.modeling.ModelingPlagiarismResult;
import de.tum.in.www1.artemis.domain.plagiarism.text.TextPlagiarismResult;
import de.tum.in.www1.artemis.repository.plagiarism.PlagiarismResultRepository;
import de.tum.in.www1.artemis.service.programming.ProgrammingLanguageFeatureService;

/**
 * Service for triggering plagiarism checks.
 */
@Service
@Component
public class PlagiarismDetectionService {

    private static final Logger log = LoggerFactory.getLogger(PlagiarismDetectionService.class);

    private final TextPlagiarismDetectionService textPlagiarismDetectionService;

    private final Optional<ProgrammingLanguageFeatureService> programmingLanguageFeatureService;

    private final ProgrammingPlagiarismDetectionService programmingPlagiarismDetectionService;

    private final ModelingPlagiarismDetectionService modelingPlagiarismDetectionService;

    private final PlagiarismResultRepository plagiarismResultRepository;

    public PlagiarismDetectionService(TextPlagiarismDetectionService textPlagiarismDetectionService, Optional<ProgrammingLanguageFeatureService> programmingLanguageFeatureService,
            ProgrammingPlagiarismDetectionService programmingPlagiarismDetectionService, ModelingPlagiarismDetectionService modelingPlagiarismDetectionService,
            PlagiarismResultRepository plagiarismResultRepository) {
        this.textPlagiarismDetectionService = textPlagiarismDetectionService;
        this.programmingLanguageFeatureService = programmingLanguageFeatureService;
        this.programmingPlagiarismDetectionService = programmingPlagiarismDetectionService;
        this.modelingPlagiarismDetectionService = modelingPlagiarismDetectionService;
        this.plagiarismResultRepository = plagiarismResultRepository;
    }

    /**
     * Check plagiarism in given text exercise
     *
     * @param exercise exercise to check plagiarism
     * @param config   configuration for plagiarism detection
     * @return result of plagiarism checks
     */
    public TextPlagiarismResult checkTextExercise(TextExercise exercise, PlagiarismDetectionConfig config) throws ExitException {
        var plagiarismResult = textPlagiarismDetectionService.checkPlagiarism(exercise, config.similarityThreshold(), config.minimumScore(), config.minimumSize());
        log.info("Finished textPlagiarismDetectionService.checkPlagiarism for exercise {} with {} comparisons,", exercise.getId(), plagiarismResult.getComparisons().size());

        trimAndSavePlagiarismResult(plagiarismResult);
        return plagiarismResult;
    }

    /**
     * Check plagiarism in given programing exercise
     *
     * @param exercise exercise to check plagiarism
     * @param config   configuration for plagiarism detection
     * @return result of plagiarism checks
     */
    public TextPlagiarismResult checkProgrammingExercise(ProgrammingExercise exercise, PlagiarismDetectionConfig config)
            throws ExitException, IOException, ProgrammingLanguageNotSupportedForPlagiarismDetectionException {
        checkProgrammingLanguageSupport(exercise);

        var plagiarismResult = programmingPlagiarismDetectionService.checkPlagiarism(exercise.getId(), config.similarityThreshold(), config.minimumScore(), config.minimumSize());
        log.info("Finished programmingExerciseExportService.checkPlagiarism call for {} comparisons", plagiarismResult.getComparisons().size());

        plagiarismResultRepository.prepareResultForClient(plagiarismResult);

        // make sure that participation is included in the exercise
        plagiarismResult.setExercise(exercise);
        return plagiarismResult;
    }

    /**
     * Check plagiarism in given programing exercise and outputs a Jplag report
     *
     * @param exercise exercise to check plagiarism
     * @param config   configuration for plagiarism detection
     * @return Jplag report of plagiarism checks
     */
    public File checkProgrammingExerciseWithJplagReport(ProgrammingExercise exercise, PlagiarismDetectionConfig config)
            throws ProgrammingLanguageNotSupportedForPlagiarismDetectionException {
        checkProgrammingLanguageSupport(exercise);
        return programmingPlagiarismDetectionService.checkPlagiarismWithJPlagReport(exercise.getId(), config.similarityThreshold(), config.minimumScore(), config.minimumSize());
    }

    /**
     * Check plagiarism in given modeling exercise
     *
     * @param exercise exercise to check plagiarism
     * @param config   configuration for plagiarism detection
     * @return result of plagiarism checks
     */
    public ModelingPlagiarismResult checkModelingExercise(ModelingExercise exercise, PlagiarismDetectionConfig config) {
        var plagiarismResult = modelingPlagiarismDetectionService.checkPlagiarism(exercise, config.similarityThreshold(), config.minimumSize(), config.minimumScore());
        log.info("Finished modelingPlagiarismDetectionService.checkPlagiarism call for {} comparisons", plagiarismResult.getComparisons().size());

        trimAndSavePlagiarismResult(plagiarismResult);
        return plagiarismResult;
    }

    private void trimAndSavePlagiarismResult(PlagiarismResult<?> plagiarismResult) {
        // Limit the amount temporarily because of database issues
        plagiarismResult.sortAndLimit(100);
        plagiarismResultRepository.savePlagiarismResultAndRemovePrevious(plagiarismResult);

        plagiarismResultRepository.prepareResultForClient(plagiarismResult);
    }

    private void checkProgrammingLanguageSupport(ProgrammingExercise exercise) throws ProgrammingLanguageNotSupportedForPlagiarismDetectionException {
        var language = exercise.getProgrammingLanguage();
        var programmingLanguageFeature = programmingLanguageFeatureService.orElseThrow().getProgrammingLanguageFeatures(language);
        if (!programmingLanguageFeature.plagiarismCheckSupported()) {
            throw new ProgrammingLanguageNotSupportedForPlagiarismDetectionException(language);
        }
    }
}