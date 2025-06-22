import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnermanageComponent } from './ownermanage.component';

describe('OwnermanageComponent', () => {
  let component: OwnermanageComponent;
  let fixture: ComponentFixture<OwnermanageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnermanageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnermanageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
