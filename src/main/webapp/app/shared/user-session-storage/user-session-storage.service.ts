import { Injectable } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { FormGroup, FormControl } from '@angular/forms';
import { IJobOffer } from '../model/job-offer.model';
import { store } from '@angular/core/src/render3';
import { JOB_OFFERS_TO_PROMOTE_SESSION, CANDIDATES_SELECTED_SESSION, LAST_CANDIDATES_SELECTED_SESSION } from '../constants/input.constants';
import { ICandidate } from '../model/candidate.model';

type KeyType = string;
type ValueType = any;

@Injectable( { providedIn: 'root' } )
export class UserSessionStorageService {

    private storageMap: Map<KeyType, ValueType>;

    constructor() {
        this.reset();
    }

    retrieve( key: KeyType ): ValueType {
        let value = null;
        if ( this.storageMap.has( key ) ) {
            value = this.storageMap.get( key );
            // console.log( 'retrieve -' + key + '- from user storage session', value );
        }
        return value;
    }

    store( key: KeyType, value: ValueType ) {
        // console.log( 'store -' + key + '- into user storage session', value );
        this.storageMap.set( key, value );
    }

    clear( key: KeyType ) {
        this.storageMap.delete( key );
    }

    reset() {
        // console.log( 'reset user storage session' );
        this.storageMap = new Map<KeyType, ValueType>();
    }

    setupJobOfferPromoteData( jobOffers: IJobOffer[] ) {
        this.store( JOB_OFFERS_TO_PROMOTE_SESSION, jobOffers );
        this.clear( LAST_CANDIDATES_SELECTED_SESSION );
        this.clear( CANDIDATES_SELECTED_SESSION );
    }

    retrieveJobOffersToPromote() {
        const jobOffers = this.retrieve( JOB_OFFERS_TO_PROMOTE_SESSION );
        return jobOffers;
    }

    storeLastCandidatesSelected( candidates: ICandidate[] ) {
        this.store( LAST_CANDIDATES_SELECTED_SESSION, candidates );
    }

    retrieveLastCandidatesSelected(): ICandidate[] {
        let candidates = this.retrieve( LAST_CANDIDATES_SELECTED_SESSION );
        if ( !candidates ) {
            candidates = [];
        }

        return candidates;
    }

    storeCandidatesSelected( candidates: ICandidate[] ) {
        this.store( CANDIDATES_SELECTED_SESSION, candidates );
    }

    retrieveCandidatesSelected(): ICandidate[] {
        let candidates = this.retrieve( CANDIDATES_SELECTED_SESSION );
        if ( !candidates ) {
            candidates = [];
        }
        return candidates;
    }
}
