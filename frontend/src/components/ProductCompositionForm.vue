<template>
  <div style="margin-bottom: 1.5rem;">
    <select v-model.number="composition.productId">
      <option value="">Select Product</option>
      <option v-for="p in products" :key="p.id" :value="p.id">{{ p.name }}</option>
    </select>
    <select v-model.number="composition.rawMaterialId">
      <option value="">Select Raw Material</option>
      <option v-for="m in materials" :key="m.id" :value="m.id">{{ m.name }}</option>
    </select>
    <input type="number" step="any" min="0.01" v-model.number="composition.quantityRequired" placeholder="Quantity per unit" />
    <button @click="saveComposition">Save</button>
  </div>
</template>

<script>
import productCompositionService from '../services/productCompositionService.js';
import productService from '../services/productService.js';
import rawMaterialService from '../services/rawMaterialService.js';

export default {
  name: 'ProductCompositionForm',
  data() {
    return {
      composition: { productId: '', rawMaterialId: '', quantityRequired: 0.01 },
      products: [],
      materials: []
    };
  },
  methods: {
    loadOptions() {
      productService.getAll().then(res => { this.products = res.data || []; });
      rawMaterialService.getAll().then(res => { this.materials = res.data || []; });
    },
    saveComposition() {
      const payload = {
        product: { id: this.composition.productId },
        rawMaterial: { id: this.composition.rawMaterialId },
        quantityRequired: this.composition.quantityRequired
      };
      if (this.composition.id) {
        productCompositionService.update(this.composition.id, payload).then(() => this.resetForm());
      } else {
        productCompositionService.create(payload).then(() => this.resetForm());
      }
      this.$emit('refresh');
    },
    editComposition(item) {
      this.composition = {
        id: item.id,
        productId: item.product?.id ?? item.productId,
        rawMaterialId: item.rawMaterial?.id ?? item.rawMaterialId,
        quantityRequired: item.quantityRequired
      };
    },
    resetForm() {
      this.composition = { productId: '', rawMaterialId: '', quantityRequired: 0.01 };
    }
  },
  mounted() {
    this.loadOptions();
  }
};
</script>
