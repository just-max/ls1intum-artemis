import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { adminState } from './admin.route';
import { ArtemisSharedModule } from 'app/shared/shared.module';
import { FormDateTimePickerModule } from 'app/shared/date-time-picker/date-time-picker.module';
import { AdminFeatureToggleComponent } from 'app/admin/features/admin-feature-toggle.component';
import { NgxDatatableModule } from '@flaviosantoro92/ngx-datatable';
import { UserManagementDetailComponent } from 'app/admin/user-management/user-management-detail.component';
import { LogsComponent } from 'app/admin/logs/logs.component';
import { HealthComponent } from 'app/admin/health/health.component';
import { ConfigurationComponent } from 'app/admin/configuration/configuration.component';
import { SystemNotificationManagementComponent } from 'app/admin/system-notification-management/system-notification-management.component';
import { SystemNotificationManagementUpdateComponent } from 'app/admin/system-notification-management/system-notification-management-update.component';
import { StatisticsComponent } from 'app/admin/statistics/statistics.component';
import { AuditsComponent } from 'app/admin/audits/audits.component';
import { HealthModalComponent } from 'app/admin/health/health-modal.component';
import { UserManagementComponent } from 'app/admin/user-management/user-management.component';
import { UserManagementUpdateComponent } from 'app/admin/user-management/user-management-update.component';
import { SystemNotificationManagementDetailComponent } from 'app/admin/system-notification-management/system-notification-management-detail.component';
import { UpcomingExamsAndExercisesComponent } from './upcoming-exams-and-exercises/upcoming-exams-and-exercises.component';
import { DocsComponent } from 'app/admin/docs/docs.component';
import { OrganizationManagementComponent } from './organization-management/organization-management.component';
import { OrganizationManagementDetailComponent } from './organization-management/organization-management-detail.component';
import { OrganizationManagementUpdateComponent } from './organization-management/organization-management-update.component';
import { ArtemisDataTableModule } from 'app/shared/data-table/data-table.module';
import { MetricsModule } from 'app/admin/metrics/metrics.module';
import { ArtemisChartsModule } from 'app/shared/chart/artemis-charts.module';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTreeModule } from '@angular/material/tree';
import { ArtemisSharedComponentModule } from 'app/shared/components/shared-component.module';
import { ReactiveFormsModule } from '@angular/forms';
import { LtiConfigurationComponent } from 'app/admin/lti-configuration/lti-configuration.component';
import { EditLtiConfigurationComponent } from 'app/admin/lti-configuration/edit-lti-configuration.component';
import { BuildAgentsComponent } from 'app/localci/build-agents/build-agents.component';
import { StandardizedCompetencyDetailComponent } from 'app/admin/standardized-competencies/standardized-competency-detail.component';
import { StandardizedCompetencyManagementComponent } from 'app/admin/standardized-competencies/standardized-competency-management.component';
import { ArtemisMarkdownEditorModule } from 'app/shared/markdown-editor/markdown-editor.module';
import { ArtemisMarkdownModule } from 'app/shared/markdown.module';
import { ArtemisCompetenciesModule } from 'app/course/competencies/competency.module';
import { UserImportModule } from 'app/shared/user-import/user-import.module';
import { SubmissionResultStatusModule } from 'app/overview/submission-result-status.module';
import { KnowledgeAreaDetailComponent } from 'app/admin/standardized-competencies/knowledge-area-detail.component';
import { AdminImportStandardizedCompetenciesComponent } from 'app/admin/standardized-competencies/import/admin-import-standardized-competencies.component';
import { KnowledgeAreaTreeComponent } from 'app/admin/standardized-competencies/knowledge-area-tree/knowledge-area-tree.component';

const ENTITY_STATES = [...adminState];

@NgModule({
    imports: [
        RouterModule.forChild(ENTITY_STATES),
        ArtemisSharedModule,
        FormDateTimePickerModule,
        NgxDatatableModule,
        ArtemisDataTableModule,
        MetricsModule,
        ArtemisChartsModule,
        MatChipsModule,
        MatAutocompleteModule,
        MatSelectModule,
        MatFormFieldModule,
        ArtemisSharedComponentModule,
        ReactiveFormsModule,
        MatTreeModule,
        ArtemisMarkdownEditorModule,
        ArtemisMarkdownModule,
        ArtemisCompetenciesModule,
        UserImportModule,
        SubmissionResultStatusModule,
    ],
    declarations: [
        AuditsComponent,
        UserManagementComponent,
        UserManagementDetailComponent,
        UserManagementUpdateComponent,
        SystemNotificationManagementComponent,
        SystemNotificationManagementDetailComponent,
        SystemNotificationManagementUpdateComponent,
        LogsComponent,
        DocsComponent,
        ConfigurationComponent,
        HealthComponent,
        HealthModalComponent,
        StatisticsComponent,
        AdminFeatureToggleComponent,
        UpcomingExamsAndExercisesComponent,
        OrganizationManagementComponent,
        OrganizationManagementDetailComponent,
        OrganizationManagementUpdateComponent,
        LtiConfigurationComponent,
        EditLtiConfigurationComponent,
        BuildAgentsComponent,
        StandardizedCompetencyDetailComponent,
        KnowledgeAreaDetailComponent,
        StandardizedCompetencyManagementComponent,
        AdminImportStandardizedCompetenciesComponent,
        KnowledgeAreaTreeComponent,
    ],
})
export class ArtemisAdminModule {}
