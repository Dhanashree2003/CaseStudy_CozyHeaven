import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminComponent } from './admin/admin.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { GuestComponent } from './guest/guest.component';
import { OwnerComponent } from './owner/owner.component';
import { RoombookingComponent } from './roombooking/roombooking.component';
import { PaymentsuccessComponent } from './paymentsuccess/paymentsuccess.component';
import { GuestManageComponent } from './guestmanage/guestmanage.component';
import { OwnerManageComponent } from './ownermanage/ownermanage.component';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    AdminComponent,
    GuestComponent,
    OwnerComponent,
    RoombookingComponent,
    PaymentsuccessComponent,
    OwnerManageComponent,
    GuestManageComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
