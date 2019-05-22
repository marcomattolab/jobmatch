import { NgModule } from '@angular/core';
import { FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, JobmatchSharedLibsModule } from './';

@NgModule({
    imports: [JobmatchSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JobmatchSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JobmatchSharedCommonModule {}
