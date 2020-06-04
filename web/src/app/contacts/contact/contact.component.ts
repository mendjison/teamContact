import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatSnackBar } from '@angular/material';
import * as model from 'src/backend/model/entities/index';
import * as repository from 'src/backend/model/repository/index';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  displayedColumns: string[] = ['index', 'firstname', 'lastname',
    'email', 'phone', 'view', 'delete'];
  dataSource: MatTableDataSource<model.Contact> = new MatTableDataSource<model.Contact>();

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(@Inject('IContactRepository') private contactService: repository.IContactRepository,
    private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit() {
    this.loadContacts();
  }

  loadContacts() {
    this.contactService.getContacts().subscribe(
      (response: HttpResponse<model.Contact[]>) => {
        this.dataSource.data = response.body;
        this.dataSource.paginator = this.paginator;
        this.dataSource.paginator._intl.itemsPerPageLabel = 'Contacts per Page';
        this.dataSource.sort = this.sort;
      },
      (er: HttpErrorResponse) => {
        console.log(er)
      });
  }

  deleteContact(element: model.Contact) {
    this.contactService.deleteContact(element.contactId)
      .subscribe((data) => {
        console.log(data);
        this.successfullDelete('Delete Contact successfull', 'deleteContact');
        this.loadContacts();
      },
        (err) => console.log(err));
  }

  createContact(): void {
    this.router.navigate(['/contacts/add']);
  }

  editContact(element) {
    this.router.navigate([`contacts/${element.contactId}`]);
  }

  successfullDelete(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

}
