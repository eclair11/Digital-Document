    
    
    vm = new Vue({
            el: '#container',
            data: () => ({
                avions: [],
                moteurs: [],
                messages: [],
            }),
            methods: {
                chargerAvions: async function () {
                    const url = `/avions`
                    const response = await fetch(url)
                    const myJson = await response.json()
                    // this c'est l'objet "vm"
                    this.avions = myJson;
                },
                
                chargerMoteurs: async function () {
                    const url = `/moteurs`
                    const response = await fetch(url)
                    const myJson = await response.json()
                    // this c'est l'objet "vm"
                    this.moteurs = myJson;
                },
                
                chargerMessages: async function () {
                    const url = `/messages`
                    const response = await fetch(url)
                    const myJson = await response.json()
                    // this c'est l'objet "vm"
                    this.messages = myJson;
                }

            }
        })
    
    
    setInterval(function(){
        vm.chargerAvions();
        vm.chargerMoteurs();
        vm.chargerMessages();
    }, 700);
    
