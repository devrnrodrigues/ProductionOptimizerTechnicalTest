<template>
  <div>
    <header>
      <h1>Production Optimizer</h1>
      <nav>
        <router-link to="/products">Products</router-link>
        <router-link to="/raw-materials">Raw Materials</router-link>
        <router-link to="/compositions">Compositions</router-link>
        <router-link to="/production">Production</router-link>
      </nav>
    </header>
    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>