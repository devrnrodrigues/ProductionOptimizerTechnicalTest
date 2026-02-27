<template>
  <div class="app-shell">
    <header class="app-header">
      <div class="app-brand">
        <h1 class="app-title">Production Optimizer</h1>
        <p class="app-subtitle">Plan and simulate your best production mix</p>
      </div>
      <nav class="app-nav">
        <router-link class="nav-link" to="/products">Products</router-link>
        <router-link class="nav-link" to="/raw-materials">Raw Materials</router-link>
        <router-link class="nav-link" to="/compositions">Compositions</router-link>
        <router-link class="nav-link" to="/production">Production</router-link>
      </nav>
    </header>
    <main class="app-main">
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>