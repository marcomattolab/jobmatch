import { Component, OnInit, OnDestroy } from '@angular/core';

import { Log } from './log.model';
import { LogsService } from './logs.service';
import { enableFooter, disableFooter } from 'app/shared';

@Component({
    selector: 'jhi-logs',
    templateUrl: './logs.component.html'
})
export class LogsComponent implements OnInit, OnDestroy {
    loggers: Log[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor(private logsService: LogsService) {
        this.filter = '';
        this.orderProp = 'name';
        this.reverse = false;
    }

    ngOnInit() {
        this.logsService.findAll().subscribe(response => (this.loggers = response.body));
        enableFooter(document);
    }

    ngOnDestroy() {
        disableFooter(document);
    }

    changeLevel(name: string, level: string) {
        const log = new Log(name, level);
        this.logsService.changeLevel(log).subscribe(() => {
            this.logsService.findAll().subscribe(response => (this.loggers = response.body));
        });
    }
}
