import { Component, OnInit, OnDestroy } from '@angular/core';

import { JhiConfigurationService } from './configuration.service';
import { enableFooter, disableFooter } from 'app/shared';

@Component({
    selector: 'jhi-configuration',
    templateUrl: './configuration.component.html'
})
export class JhiConfigurationComponent implements OnInit, OnDestroy {
    allConfiguration: any = null;
    configuration: any = null;
    configKeys: any[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor(private configurationService: JhiConfigurationService) {
        this.configKeys = [];
        this.filter = '';
        this.orderProp = 'prefix';
        this.reverse = false;
    }

    keys(dict): Array<string> {
        return dict === undefined ? [] : Object.keys(dict);
    }

    ngOnInit() {
        this.configurationService.get().subscribe(configuration => {
            this.configuration = configuration;

            for (const config of configuration) {
                if (config.properties !== undefined) {
                    this.configKeys.push(Object.keys(config.properties));
                }
            }
        });

        this.configurationService.getEnv().subscribe(configuration => {
            this.allConfiguration = configuration;
        });

        enableFooter(document);
    }

    ngOnDestroy() {
        disableFooter(document);
    }
}
