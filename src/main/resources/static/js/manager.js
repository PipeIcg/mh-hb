

const app = new Vue({
    el: '#app',
    data:{
        clients:[],
        pre: [],
        form:{
            email: '',
            first_name:'',
            last_name: '',
            password: ''
        },
        borrar:""
    },
    created(){
      this.loadData();
    },
    methods:{
        loadData(){
            axios.get('/rest/clients')
        .then(res => {
            this.clients = res.data._embedded.clients
            this.pre = res.data
            console.log(this.pre)
            
            
        })
        .catch(error => {
            console.log(error)
        }) 
        },
        addClient(){
            
            axios.post('/rest/clients',{
                email: this.form.email,
                first_name: this.form.first_name,
                last_name: this.form.last_name,
                password: this.form.password
            })
            .then(res => {
                this.form.email = ''
                this.form.first_name = ''
                this.form.last_name = ''
                this.form.password = ''
                this.loadData()
            })
            .catch(err => console.log(err))
        },
        deleteClient(url){
            axios.delete(url)
            .then(res=>{
                this.loadData()
            })
            .catch(e=>console.log(e))
            
        console.log("aun no esta listo :D")
        },
        newLoanPage(){
            return window.location.href = "/web/loanAdmin.html"
        }
    }
})

