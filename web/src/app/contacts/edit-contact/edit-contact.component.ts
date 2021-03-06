import { Component, OnInit, Inject } from '@angular/core';
import * as model from 'src/backend/model/entities/index';
import * as repository from 'src/backend/model/repository/index';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-edit-contact',
  templateUrl: './edit-contact.component.html',
  styleUrls: ['./edit-contact.component.css']
})
export class EditContactComponent implements OnInit {
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
  contactInit: model.Contact = null;
  editable: boolean = true;
  errorMessage: string = '';
  isEditError: boolean = false;

  constructor(private fb: FormBuilder,
    private router: Router,
    @Inject('IContactRepository') private contactService: repository.IContactRepository,
    private _snackBar: MatSnackBar,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.isEditError = false;
    let id = this.route.snapshot.paramMap.get('id');
    this.contactService.getContactById(id)
      .subscribe(
        (response: HttpResponse<model.Contact>) => {
          this.contactInit = response.body;
          this.contactForm.patchValue(response.body);
        }
      )
  }

  editContact(): void {
    let contact: model.Contact = this.contactForm.value;
    contact.contactId = this.contactInit.contactId;
    this.contactService.updateContact(this.contactInit.contactId, contact).subscribe(

      (httpContact: HttpResponse<model.Contact>) => {
        this.successfullEdit('Update Contact successfull', 'editContact');
        this.backToContacts()
        this.contactForm.reset();
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.errorMessage = httpErrorResponse.error.message;
        if (this.errorMessage === '' || this.errorMessage === undefined) {
          this.errorMessage = 'email or Phone or firstname and lastname already exist';
        }
        this.isEditError = true;
      }
    );
  }

  edit(): void {
    this.editable = false;
  }

  backToContacts() {
    this.router.navigate(['/contacts']);
  }

  successfullEdit(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }


}
