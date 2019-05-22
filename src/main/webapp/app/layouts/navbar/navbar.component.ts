
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VERSION } from 'app/app.constants';
import { AccountService, JhiLanguageHelper, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { FileService } from 'app/shared/file/file.service';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';
import { JhiEventManager, JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

@Component( {
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.scss']
} )
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private eventManager: JhiEventManager,
        private router: Router,
        private fileService: FileService,
        private searchFilterParamsService: SearchFilterParamsService,
        private userSessionStorageService: UserSessionStorageService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then( languages => {
            this.languages = languages;
        } );

        this.profileService.getProfileInfo().then( profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        } );
    }

    changeLanguage( languageKey: string ) {
        this.sessionStorage.store( 'locale', languageKey );
        this.languageService.changeLanguage( languageKey );
        this.eventManager.broadcast( {
            name: 'languageModified',
            content: 'Language Modified'
        } );
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate( [''] );
        this.searchFilterParamsService.reset();
        this.userSessionStorageService.reset();
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() && this.accountService.getImageUrl() ? this.fileService.getDownloadFileUrl( this.accountService.getImageUrl() ) : null;
    }

    getLoggedUsername() {
        const username = this.accountService.getUsername();
        return username ? `${username}` : '';
    }

    getCurrentRoleId() {
        return this.accountService.getCurrentRoleId();
    }

    isCurrentRoleCandidate() {
        return this.accountService.isCurrentRoleCandidate();
    }

    isCurrentRoleCompany() {
        return this.accountService.isCurrentRoleCompany();
    }

    isCurrentRoleSponsoringInstitution() {
        return this.accountService.isCurrentRoleSponsoringInstitution();
    }

    isCurrentRoleAdmin() {
        return this.accountService.isCurrentRoleAdmin();
    }
}
