<template>
  <div>
    <h2>Product Compositions</h2>
    <p style="margin-bottom: 0.5rem;">Define which raw materials (and how much) are needed to produce 1 unit of each product.</p>
    <ProductCompositionForm ref="form" @refresh="fetchCompositions" />
    <p v-if="!loading && !compositions.length">No compositions registered. Add products and raw materials first, then create a composition above.</p>
    <p v-if="loading">Loading...</p>
    <table v-else-if="compositions.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Product</th>
          <th>Raw Material</th>
          <th>Quantity per unit</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="c in compositions" :key="c.id">
          <td>{{ c.id }}</td>
          <td>{{ c.product?.name || '-' }}</td>
          <td>{{ c.rawMaterial?.name || '-' }}</td>
          <td>{{ c.quantityRequired }}</td>
          <td>
            <button @click="editComposition(c)">Edit</button>
            <button @click="deleteComposition(c.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import ProductCompositionForm from './ProductCompositionForm.vue';
import productCompositionService from '../services/productCompositionService.js';

export default {
  components: { ProductCompositionForm },
  data() {
    return { compositions: [], loading: false };
  },
  methods: {
    fetchCompositions() {
      this.loading = true;
      productCompositionService
        .getAll()
        .then(res => (this.compositions = res.data || []))
        .catch(() => (this.compositions = []))
        .finally(() => (this.loading = false));
    },
    deleteComposition(id) {
      if (confirm('Are you sure?')) {
        productCompositionService
          .delete(id)
          .then(this.fetchCompositions)
          .catch(() => this.fetchCompositions());
      }
    },
    editComposition(item) {
      this.$refs.form.editComposition(item);
    }
  },
  mounted() {
    this.fetchCompositions();
  }
};
</script>
