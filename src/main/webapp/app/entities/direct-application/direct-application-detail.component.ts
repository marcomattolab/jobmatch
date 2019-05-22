import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDirectApplication } from 'app/shared/model/direct-application.model';

@Component({
    selector: 'jhi-direct-application-detail',
    templateUrl: './direct-application-detail.component.html'
})
export class DirectApplicationDetailComponent implements OnInit {
    directApplication: IDirectApplication;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ directApplication }) => {
            this.directApplication = directApplication;
        });
    }

    previousState() {
        window.history.back();
    }
}
