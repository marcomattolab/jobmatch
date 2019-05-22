import { Injectable } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { FormGroup, FormControl } from '@angular/forms';

type PathType = string;
type ParamsType = Map<string, any>;

@Injectable( { providedIn: 'root' } )
export class SearchFilterParamsService {
    private pathParamsMap: Map<PathType, ParamsType>;

    constructor() {
        this.reset();
    }

    retrievePathParams( path: PathType ): Map<string, any> {
        return this.hasPresetParams( path ) ? this.pathParamsMap.get( path ) : null;
    }

    storePathParams( path: PathType, params: Map<string, any> ) {
        this.pathParamsMap.set( path, params );
    }

    patchFormWithParams( path: PathType, formGroup: FormGroup ) {
        const searchFilterParams = this.retrievePathParams( path );

        if ( searchFilterParams ) {
            const patchValue = {};

            searchFilterParams.forEach(( value, key ) => {
                if ( formGroup.contains( key ) ) {
                    patchValue[key] = value;
                } else {
                    formGroup.controls[key] = new FormControl( value );
                }
            } );

            formGroup.patchValue( patchValue );
            formGroup.updateValueAndValidity();
        }
    }

    storePathFormParams( path: PathType, formGroup: FormGroup ) {
        const params = new Map<string, any>();

        for ( const controlsKey in formGroup.controls ) {
            const control = formGroup.controls[controlsKey];
            const controlValue = control.value;
            if ( !isNullOrUndefined( controlValue ) ) {
                if ( typeof controlValue === 'string' || controlValue instanceof String ) {
                    if ( '' !== controlValue.trim() ) {
                        params.set( controlsKey, controlValue );
                    }
                } else {
                    params.set( controlsKey, controlValue );
                }
            }
        }

        if ( params.size > 0 ) {
            this.pathParamsMap.set( path, params );
        } else {
            this.clearPathFormParams( path );
        }
    }

    clearPathFormParams( path: PathType ) {
        this.pathParamsMap.delete( path );
    }

    hasPresetParams( path: PathType ): boolean {
        const searchFilterParams = this.pathParamsMap.get( path );

        if ( isNullOrUndefined( searchFilterParams ) ) {
            return false;
        } else {
            if ( searchFilterParams.size === 0 ) {
                return false;
            } else {
                let foundParameter = false;
                searchFilterParams.forEach(( value, key ) => {
                    if ( typeof value === 'string' || value instanceof String ) {
                        if ( '' !== value.trim() ) {
                            foundParameter = true;
                        }
                    } else {
                        foundParameter = true;
                    }
                } );
                return foundParameter;
            }
        }
    }

    reset() {
        this.pathParamsMap = new Map<PathType, ParamsType>();
    }
}
