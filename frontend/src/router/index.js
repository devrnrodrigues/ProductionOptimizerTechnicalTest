import { createRouter, createWebHistory } from 'vue-router';
import ProductsView from '../views/ProductsView.vue';
import RawMaterialsView from '../views/RawMaterialsView.vue';
import CompositionsView from '../views/CompositionsView.vue';
import ProductionView from '../views/ProductionView.vue';

const routes = [
  { path: '/', redirect: '/products' },
  { path: '/products', component: ProductsView },
  { path: '/raw-materials', component: RawMaterialsView },
  { path: '/compositions', component: CompositionsView },
  { path: '/production', component: ProductionView },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;