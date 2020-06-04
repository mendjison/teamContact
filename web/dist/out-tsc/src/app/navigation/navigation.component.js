import * as tslib_1 from "tslib";
import { Component } from '@angular/core';
import { Breakpoints } from '@angular/cdk/layout';
import { map, shareReplay } from 'rxjs/operators';
let NavigationComponent = class NavigationComponent {
    constructor(breakpointObserver) {
        this.breakpointObserver = breakpointObserver;
        this.isHandset$ = this.breakpointObserver.observe(Breakpoints.Handset)
            .pipe(map(result => result.matches), shareReplay());
    }
};
NavigationComponent = tslib_1.__decorate([
    Component({
        selector: 'app-navigation',
        templateUrl: './navigation.component.html',
        styleUrls: ['./navigation.component.scss']
    })
], NavigationComponent);
export { NavigationComponent };
//# sourceMappingURL=navigation.component.js.map