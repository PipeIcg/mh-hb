const app = new Vue({
    el: "#app",
    data:{
        debitCard:[],
        creditCard:[],
        cards:[],
        clients:[],
        debitTrue:[],
        creditTrue:[],
        creditTrueLength:0,
        debitTrueLength:0
    }, 
    created(){
        this.loadData()

    },
    methods:{
        loadData(){
            axios.get('/api/clients/current')
            .then(res => {
                this.clients = res.data
               this.cards = res.data.cards
               this.creditCard = this.cards.filter(card => card.type == 'CREDIT')
               this.debitCard = this.cards.filter(card => card.type == 'DEBIT')
               this.debitTrue = this.debitCard.filter(card => card.state == true)
               this.creditTrue = this.creditCard.filter(card => card.state == true)
               this.creditTrueLength = this.creditTrue.length
               this.debitTrueLength = this.debitTrue.length
               
                
                console.log(this.clients)
                console.log(this.cards)
                
            })
            .catch(e => console.log(e))
        },
        deleteCard(id){
            axios.patch(`/api/clients/current/cards/delete/${id}`)
            
            Swal.fire({
                title: 'Card deleted!',
                icon: "success",
                showConfirmButton: false,
                timer: 2000,
            })
            .then(res => {

                return window.location.href = "/web/cards.html"
                console.log(res)
                console.log("Tarjeta eliminada :D")
            })
            .catch(e => console.log(e))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => {
                console.log('signed out!!!')
                return window.location.href = "/web/index.html"
            })
            .catch(e => console.log(e))
        }
        
    }
})