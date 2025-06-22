import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestManageComponent } from './guestmanage.component';

describe('GuestmanageComponent', () => {
  let component: GuestManageComponent;
  let fixture: ComponentFixture<GuestManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuestManageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GuestManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
