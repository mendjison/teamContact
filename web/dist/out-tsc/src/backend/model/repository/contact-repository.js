import * as tslib_1 from "tslib";
import { Injectable } from '@angular/core';
import { ServerInfo } from './server-info';
let ContactRepository = class ContactRepository {
    constructor(httpClient) {
        this.httpClient = httpClient;
        this.url = new ServerInfo('contacts').url;
    }
    getContacts() {
        return this.httpClient.get(this.url, { observe: 'response' });
    }
    getContactById(contactId) {
        return this.httpClient
            .get(`${this.url}/${contactId}`, { observe: 'response' });
    }
    saveContact(contact) {
        contact.contactId = '';
        return this.httpClient
            .post(`${this.url}`, contact, { observe: 'response' });
    }
    updateContact(contactId, contact) {
        return this.httpClient
            .put(`${this.url}/${contactId}`, contact, { observe: 'response' });
    }
    deleteContact(contactId) {
        return this.httpClient
            .delete(`${this.url}/${contactId}`, { observe: 'response' });
    }
};
ContactRepository = tslib_1.__decorate([
    Injectable({
        providedIn: 'root'
    })
], ContactRepository);
export { ContactRepository };
//# sourceMappingURL=contact-repository.js.map