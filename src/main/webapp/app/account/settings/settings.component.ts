import { Component, OnInit, OnDestroy } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService, JhiLanguageHelper } from 'app/core';
import { enableFooter, disableFooter } from 'app/shared';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit, OnDestroy {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];

    constructor(
        private accountService: AccountService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.settingsAccount = this.copyAccount(account);
        });
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        enableFooter(document);
    }

    ngOnDestroy() {
        disableFooter(document);
    }

    save() {
        this.accountService.save(this.settingsAccount).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
                this.accountService.identity(true).then(account => {
                    this.settingsAccount = this.copyAccount(account);
                });
                this.languageService.getCurrent().then(current => {
                    if (this.settingsAccount.langKey !== current) {
                        this.languageService.changeLanguage(this.settingsAccount.langKey);
                    }
                });
            },
            () => {
                this.success = null;
                this.error = 'ERROR';
            }
        );
    }

    copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl
        };
    }
}
