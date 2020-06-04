import { Injectable } from '@angular/core';
import { IContactRepository } from './i-contact-repository';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { ServerInfo } from './server-info';
import { Observable } from 'rxjs';
import * as entities from 'src/backend/model/entities/index';

@Injectable({
  providedIn: 'root'
})
export class ContactRepository implements IContactRepository {

  private url: string;
  private headers: HttpHeaders = new HttpHeaders();
  constructor(private httpClient: HttpClient) {
    this.url = new ServerInfo('contacts').url;
    this.headers.set('errorMessage', '');
  }
  getContacts(): Observable<HttpResponse<entities.Contact[]>> {
    return this.httpClient.get<entities.Contact[]>(this.url, { observe: 'response' });
  }
  getContactById(contactId: string): Observable<HttpResponse<entities.Contact>> {
    return this.httpClient
      .get<entities.Contact>(`${this.url}/${contactId}`, { observe: 'response' });
  }
  saveContact(contact: entities.Contact): Observable<HttpResponse<entities.Contact>> {
    contact.contactId = '';
    return this.httpClient
      .post<entities.Contact>(`${this.url}`, contact, 
        { headers: this.headers, observe: 'response' });
  }
  updateContact(contactId: string, contact: entities.Contact): Observable<HttpResponse<entities.Contact>> {
    return this.httpClient
      .put<entities.Contact>(`${this.url}/${contactId}`, contact, { observe: 'response' });
  }
  deleteContact(contactId: string): Observable<HttpResponse<boolean>> {
    return this.httpClient
      .delete<boolean>(`${this.url}/${contactId}`, { observe: 'response' });
  }
}
