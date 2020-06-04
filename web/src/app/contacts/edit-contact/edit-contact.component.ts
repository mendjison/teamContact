import { Component, OnInit, Inject } from '@angular/core';
import * as model from 'src/backend/model/entities/index';
import * as repository from 'src/backend/model/repository/index';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { HttpResponse } from '@angular/common/http';

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

  //, Validators.compose([Validators.minLength(7), Validators.maxLength(8)])
  constructor(private fb: FormBuilder,
    private router: Router,
    @Inject('IContactRepository') private contactService: repository.IContactRepository,
    private _snackBar: MatSnackBar,
    private route: ActivatedRoute) { }

  ngOnInit() {
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
        this.router.navigate(['/contacts']);
      },
      (err) => console.log(err)
    );
    this.contactForm.reset();
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
