package de.tum.in.www1.artemis.service.dto;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.tum.in.www1.artemis.domain.BuildJob;
import de.tum.in.www1.artemis.domain.Result;
import de.tum.in.www1.artemis.domain.enumeration.AssessmentType;
import de.tum.in.www1.artemis.domain.enumeration.BuildStatus;
import de.tum.in.www1.artemis.domain.enumeration.RepositoryType;
import de.tum.in.www1.artemis.web.rest.dto.ParticipationDTO;
import de.tum.in.www1.artemis.web.rest.dto.ResultDTO;
import de.tum.in.www1.artemis.web.rest.dto.SubmissionDTO;

/**
 * A DTO representing a finished build job
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record FinishedBuildJobDTO(String id, String name, String buildAgentAddress, long participationId, long courseId, long exerciseId, BuildStatus status,
        RepositoryType repositoryType, String repositoryName, RepositoryType triggeredByPushTo, ZonedDateTime buildStartDate, ZonedDateTime buildCompletionDate, String commitHash,
        ResultDTO submissionResult) {

    /**
     * A DTO representing a result
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record ResultDTO(Long id, ZonedDateTime completionDate, Boolean successful, Double score, Boolean rated, ParticipationDTO participation, SubmissionDTO submission,
            AssessmentType assessmentType, Integer testCaseCount, Integer passedTestCaseCount, Integer codeIssueCount) {

        /**
         * Converts a Result into a ResultDTO
         *
         * @param result to convert
         * @return the converted DTO
         */
        public static ResultDTO of(Result result) {
            SubmissionDTO submissionDTO = result.getSubmission() == null ? null : SubmissionDTO.of(result.getSubmission());

            return new ResultDTO(result.getId(), result.getCompletionDate(), result.isSuccessful(), result.getScore(), result.isRated(),
                    ParticipationDTO.of(result.getParticipation()), submissionDTO, result.getAssessmentType(), result.getTestCaseCount(), result.getPassedTestCaseCount(),
                    result.getCodeIssueCount());
        }
    }

    /**
     * Converts a Page of BuildJobs into a Page of FinishedBuildJobDTOs
     *
     * @param buildJobs to convert
     * @return the converted Page
     */
    public static Page<FinishedBuildJobDTO> fromBuildJobsPage(Page<BuildJob> buildJobs) {
        return buildJobs.map(FinishedBuildJobDTO::of);
    }

    /**
     * Converts a BuildJob into a FinishedBuildJobDTO
     *
     * @param buildJob to convert
     * @return the converted DTO
     */
    public static FinishedBuildJobDTO of(BuildJob buildJob) {
        ResultDTO resultDTO = buildJob.getResult() == null ? null : ResultDTO.of(buildJob.getResult());

        return new FinishedBuildJobDTO(buildJob.getBuildJobId(), buildJob.getName(), buildJob.getBuildAgentAddress(), buildJob.getParticipationId(), buildJob.getCourseId(),
                buildJob.getExerciseId(), buildJob.getBuildStatus(), buildJob.getRepositoryType(), buildJob.getRepositoryName(), buildJob.getRepositoryType(),
                buildJob.getBuildStartDate(), buildJob.getBuildCompletionDate(), buildJob.getCommitHash(), resultDTO);
    }
}
