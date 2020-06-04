import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import * as repository from 'src/backend/model/repository/index'



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    { provide: 'IContactRepository', useClass: repository.ContactRepository }
  ]
})
export class BackendModule { }
