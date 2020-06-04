import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import * as entities from 'src/backend/model/entities/index';

export interface IContactRepository {
    getContacts(): Observable<HttpResponse<entities.Contact[]>>;
    getContactById(contactId: string): Observable<HttpResponse<entities.Contact>>;
    saveContact(contact: entities.Contact): Observable<HttpResponse<entities.Contact>>;
    updateContact(contactId: string, contact: entities.Contact): Observable<HttpResponse<entities.Contact>>;
    deleteContact(contactId: string): Observable<HttpResponse<boolean>>;
}