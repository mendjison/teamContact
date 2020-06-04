import * as tslib_1 from "tslib";
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BackendModule } from 'src/backend/backend.module';
import { NavigationComponent } from './navigation/navigation.component';
let AppModule = class AppModule {
};
AppModule = tslib_1.__decorate([
    NgModule({
        declarations: [
            AppComponent, NavigationComponent
        ],
        imports: [
            BrowserModule,
            ReactiveFormsModule,
            FormsModule,
            HttpClientModule,
            BackendModule,
            AppRoutingModule,
            MaterialModule,
            BrowserAnimationsModule
        ],
        providers: [],
        bootstrap: [AppComponent]
    })
], AppModule);
export { AppModule };
//# sourceMappingURL=app.module.js.map