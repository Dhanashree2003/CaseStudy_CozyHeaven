// import { NgModule } from '@angular/core';
// import { RouterModule, Routes } from '@angular/router';
// import { AuthComponent } from './auth/auth.component';

// const routes: Routes = [
// { path: 'auth', component: AuthComponent },
// { path: '', redirectTo: '/auth', pathMatch: 'full' }


// ];

// @NgModule({
//   imports: [RouterModule.forRoot(routes)],
//   exports: [RouterModule]
// })
// export class AppRoutingModule { }


import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AuthComponent } from './auth/auth.component';
import { GuestComponent } from './guest/guest.component';
import { OwnerComponent } from './owner/owner.component';
import { PaymentsuccessComponent } from './paymentsuccess/paymentsuccess.component';
import { RoombookingComponent } from './roombooking/roombooking.component';
import { GuestManageComponent } from './guestmanage/guestmanage.component';
import { OwnerManageComponent } from './ownermanage/ownermanage.component';

const routes: Routes = [
  { path: '', component: AuthComponent },
  { path: 'login', component: AuthComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'guest', component: GuestComponent },
  { path: 'owner', component: OwnerComponent },
  { path: 'roombooking/:hotelId', component: RoombookingComponent },
  { path: 'roombooking', component: RoombookingComponent },
  {path: 'paymentsuccess',component:PaymentsuccessComponent},
  { path: 'guests', component: GuestManageComponent },
  { path: 'owners', component: OwnerManageComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
