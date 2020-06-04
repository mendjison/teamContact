import { HttpHeaders } from '@angular/common/http';
export class ServerInfo {
    public url: string = 'http://localhost:8080/api/';
    constructor(path: string) {
        this.url = this.url + path;
    }
}