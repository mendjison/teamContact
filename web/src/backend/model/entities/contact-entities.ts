
export class Address {
    public street?: string;
    public housNo?: number;
    public poBox?: number;
    public city?: string;
    public country?: string;

    constructor(street?: string, housNo?: number, poBox?: number, city?: string, country?: string) {
        this.street = street;
        this.housNo = housNo;
        this.poBox = poBox;
        this.city = city;
        this.country = country;
    }
}

export class Contact {
    public contactId?: string;
    public firstname: string;
    public lastname: string;
    public email: string;
    public phone?: string;
    public address?: Address;

    constructor(firstname: string, lastname: string, email: string, contactId?: string,
        phone?: string, adress?: Address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = adress
    }
}