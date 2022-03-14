const app = new Vue({
    el: '#app',
    data: {
        register: false,
        email: '',
        password: '',
        first_name:'',
        last_name:''
        /* nuevoRegistro:{
            first_name:'',
            last_name:'',
            email:'',
            password:''
        } */
    },
    methods: {
        login() {
            axios.post('/api/login', `email=${this.email}&password=${this.password}`,
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('signed in!!!')
                    return window.location.href = " /web/home.html"
                })
                .catch(e => console.log(e))
        },
        registrar(register) {
            this.register = register
        },
        signUp() {
            axios.post('/api/clients', `first_name=${this.first_name}&last_name=${this.last_name}&email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
            .then(response => {
               
                axios.post('/api/login', `email=${this.email}&password=${this.password}`,
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    console.log('signed in!!!')
                    return window.location.href = " /web/home.html"
                })

                
            })
            .catch(e => console.log(e))
        }
    }
})