import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'playlist',
        loadChildren: () => import('./playlist/playlist.module').then(m => m.ApolloPlaylistModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ApolloEntityModule {}
