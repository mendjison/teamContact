import { HttpHeaders } from '@angular/common/http';
export class ServerInfo {
    public url: string = 'http://localhost:9897/api/';
    constructor(path: string) {
        this.url = this.url + path;
    }
}