import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BidupModalComponent } from './bidup-modal.component';

describe('BidupModalComponent', () => {
  let component: BidupModalComponent;
  let fixture: ComponentFixture<BidupModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BidupModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BidupModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
