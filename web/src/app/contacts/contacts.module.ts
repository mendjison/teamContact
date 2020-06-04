import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactComponent } from './contact/contact.component';
import { AddContactComponent } from './add-contact/add-contact.component';
import { EditContactComponent } from './edit-contact/edit-contact.component';
import { Routes, RouterModule } from '@angular/router';
import { MaterialModule } from '../material-module';
import { ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [
  { path: 'contacts', component: ContactComponent},
  { path: 'contacts/add', component: AddContactComponent },
  { path: 'contacts/:id', component: EditContactComponent },
];

@NgModule({
  declarations: [ContactComponent, AddContactComponent, EditContactComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ]
})
export class ContactsModule { }
