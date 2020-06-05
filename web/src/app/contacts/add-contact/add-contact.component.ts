import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';

import * as model from 'src/backend/model/entities/index';
import * as repository from 'src/backend/model/repository/index';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.css']
})
export class AddContactComponent implements OnInit {
  contactForm: FormGroup = this.fb.group({
    firstname: ['', Validators.required],
    lastname: ['', Validators.required],
    email: ['', Validators.required],
    phone: ['', Validators.required],
    address: this.fb.group({
      street: [''],
      housNo: [0],
      poBox: [0],
      city: [''],
      country: ['']
    })
  });
  errorMessage: string;
  isEditError: boolean = false;
  constructor(private fb: FormBuilder,
    private router: Router,
    @Inject('IContactRepository') private contactService: repository.IContactRepository,
    private _snackBar: MatSnackBar) { }

  ngOnInit() {
    this.isEditError = false;
    this.errorMessage = '';
  }

  createContact(): void { 
    let contact: model.Contact = this.contactForm.value;
    this.contactService.saveContact(contact).subscribe(

      (httpResponse: HttpResponse<model.Contact>) => {
        this.successfullSave('Save Contact successfull', 'createSession');
        this.contactForm.reset();
        this.backToContacts();
      },
      (httpErrorResponse: HttpErrorResponse)  => {
        this.errorMessage = httpErrorResponse.error.message;
        this.isEditError = true;
      }
    );
  }

  cancel(): void {
    this.contactForm.reset();
  }

  backToContacts() {
    this.router.navigate(['/contacts']);
  }

  successfullSave(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

}
