const app = new Vue({
    el:'#app',
    data:{
        tipoTarjeta:'',
        color:'',
        cards:[],
        creditCard:[],
        debitCard:[],
        debitTrue:[],
        creditTrue:[],
        creditTrueLength: 0
    },
    created(){
        this.loadData()
    },
    methods:{
        createCard(){
            axios.post('/api/clients/current/cards', `type=${this.tipoTarjeta}&color=${this.color}`,
            {headers:{'content-type': 'application/x-www-form-urlencoded'}})
            .then(res =>{
                console.log("tarjeta creada :3")
                return window.location.href = "/web/cards.html"
            })
            .catch(error => {
                console.log(error.message)
            })
        },
        loadData(){
            axios.get('/api/clients/current')
            .then(res => {
            
               this.cards = res.data.cards
               this.creditCard = this.cards.filter(card => card.type == 'CREDIT')
               this.debitCard = this.cards.filter(card => card.type == 'DEBIT')
               this.debitTrue = this.debitCard.filter(card => card.state == true)
               this.creditTrue = this.creditCard.filter(card => card.state == true)
               this.creditTrueLength = this.creditTrue.length
                
                console.log(this.clients)
                console.log(this.cards)
                
            })
            .catch(e => console.log(e))
    }}
    
})