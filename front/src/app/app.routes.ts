import { Routes } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';

export const routes: Routes = [
  { path: '', component: UserListComponent },
  { path: 'usuario/novo', component: UserFormComponent },
  { path: 'usuario/:id', component: UserDetailComponent },
  { path: 'usuario/:id/editar', component: UserFormComponent },
];
