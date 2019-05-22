interface Array<T> {
    remove( elem: T ): boolean;
    pushIf( elem: T, condition: ( els: T ) => boolean ): boolean;
    removeUniqueIf( condition: ( els: T ) => boolean ): boolean;
}

if ( !Array.prototype.remove ) {
    Array.prototype.remove = function <T>( elem: T ): boolean {
        const index = this.indexOf( elem, 0 );
        if ( index === -1 ) {
            return false;
        }

        this.splice( index, 1 );
        return true;
    };
}

if ( !Array.prototype.pushIf ) {
    Array.prototype.pushIf = function <T>( elem: T, condition: ( els: T ) => boolean ): boolean {
        for ( const el of this ) {
            if ( !condition( el ) ) {
                return false;
            }
        }

        this.push( elem );
        return true;
    };
}

if ( !Array.prototype.removeUniqueIf ) {
    Array.prototype.removeUniqueIf = function <T>( condition: ( els: T ) => boolean ): boolean {
        const currArray: Array<T> = this;
        for ( let i = 0; i < currArray.length; i++ ) {
            if ( condition( currArray[i] ) ) {
                currArray.splice( i, 1 );
                return true;
            }
        }

        return false;
    };
}
