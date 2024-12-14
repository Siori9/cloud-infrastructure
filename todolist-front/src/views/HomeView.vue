<script lang="ts">
import { defineComponent } from 'vue';

// Components
import MenuConnection from "@/components/MenuConnection.vue";
import HeaderMenu from "@/components/HeaderMenu.vue";

export default defineComponent({
  name: 'HomeView',

  components: {
    HeaderMenu,
    MenuConnection,
  },
});
</script>

<template>
    <header-menu/>
    <menu-connection class="pa-3 text-h5"/>
</template>


