import { TestBed } from '@angular/core/testing';

import { ContactRepository } from './contact-repository';

describe('ContactRepositoryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ContactRepository = TestBed.get(ContactRepository);
    expect(service).toBeTruthy();
  });
});
