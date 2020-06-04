import * as tslib_1 from "tslib";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import * as repository from 'src/backend/model/repository/index';
let BackendModule = class BackendModule {
};
BackendModule = tslib_1.__decorate([
    NgModule({
        declarations: [],
        imports: [
            CommonModule,
            HttpClientModule
        ],
        providers: [
            { provide: 'IContactRepository', useClass: repository.ContactRepository }
        ]
    })
], BackendModule);
export { BackendModule };
//# sourceMappingURL=backend.module.js.map