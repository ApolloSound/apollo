import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ApolloSharedModule } from 'app/shared/shared.module';

import { SfMetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [ApolloSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [SfMetricsComponent]
})
export class MetricsModule {}
