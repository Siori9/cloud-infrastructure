<script lang="ts">
import {defineComponent} from 'vue'
import HeaderMenu from "@/components/HeaderMenu.vue";

export default defineComponent({
  name: 'App',
  components: {HeaderMenu},
})
</script>

<template>
  <v-app>
    <v-main>
      <div class="fill-height align-center d-flex items-center" id="background">
        <router-view/>
      </div>
    </v-main>
  </v-app>
</template>

<style>
#background {
  background-color: #2f2f2f;
}
</style>


