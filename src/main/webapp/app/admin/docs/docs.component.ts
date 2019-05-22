import { Component, OnInit, OnDestroy } from '@angular/core';
import { enableFooter, disableFooter } from 'app/shared';

@Component({
    selector: 'jhi-docs',
    templateUrl: './docs.component.html'
})
export class JhiDocsComponent implements OnInit, OnDestroy {
    constructor() {}

    ngOnInit() {
        enableFooter(document);
    }

    ngOnDestroy() {
        disableFooter(document);
    }
}
