import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <h1>Usuários</h1>
    <button routerLink="/usuario/novo">Novo Usuário</button>
    <ul>
      <li *ngFor="let user of usuarios">
        {{ user.nome }} - {{ user.email }}
        <a [routerLink]="['/usuario', user.id]">Detalhes</a>
        <a [routerLink]="['/usuario', user.id, 'editar']">Editar</a>
      </li>
    </ul>
  `
})
export class UserListComponent implements OnInit {
  usuarios: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.listar().subscribe({
      next: (res) => this.usuarios = res,
      error: (err) => console.error('Erro ao buscar usuários:', err)
    });
  }
}
