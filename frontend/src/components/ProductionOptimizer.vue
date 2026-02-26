<template>
  <div>
    <h2>Production Optimization</h2>
    <button @click="optimize" :disabled="loading">Optimize Production</button>

    <p v-if="loading">Loading...</p>
    <p v-else-if="error" class="error">{{ error }}</p>
    <p v-else-if="hasSearched && !plan.length">No production suggested. Add raw materials, products and their compositions.</p>

    <table v-else-if="plan.length">
      <thead>
        <tr>
          <th>Product</th>
          <th>Quantity to Produce</th>
          <th>Expected Profit</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in plan" :key="p.productName">
          <td>{{ p.productName }}</td>
          <td>{{ p.quantityToProduce }}</td>
          <td>{{ p.expectedProfit }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import productionService from '../services/productionService.js';

export default {
  data() {
    return {
      plan: [],
      loading: false,
      error: '',
      hasSearched: false
    };
  },
  methods: {
    optimize() {
      this.loading = true;
      this.error = '';
      this.hasSearched = true;
      productionService.optimize()
        .then(res => {
          this.plan = Array.isArray(res.data) ? res.data : [];
        })
        .catch(err => {
          console.error(err);
          this.error = err.response?.data?.message || err.message || 'Could not reach server. Is the backend running on http://localhost:8080?';
          this.plan = [];
        })
        .finally(() => { this.loading = false; });
    }
  }
};
</script>

<style scoped>
.error { color: #c00; }
</style>