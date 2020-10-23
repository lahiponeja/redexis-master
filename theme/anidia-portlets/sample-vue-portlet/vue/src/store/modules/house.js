import { http } from '../../services/http/index'
// import { reactive, readonly } from 'vue'
import { reactive, shallowReadonly } from '@vue/composition-api'
import coverageService from '../../services/coverageServices'
import xmlToJsonImp from '../../helpers/xmlToJsonImp'

const state = reactive({
  postalCode: "",
  houseType: "",
  houseSteps: [
    {
      name: "cobertura",
      icon: "an-icon--lightning-borders",
      heading: {
        title: "TU AHORRO EMPIEZA AQUÍ",
        subtitle: "Disfruta de un calor homogéneo y una temperatura constante en el agua caliente gracias a un suministro sin interrupciones que no ocupa espacio de almacenamiento",
      },
      active: true,
    },
    {
      name: "vivienda",
      icon: "an-icon--living-place",
      heading: {
        title: "SOLICITUD ONLINE",
        subtitle: "Cuéntanos cómo es tu hogar o negocio para que podamos redefinir tu energía",
      },
      active: false,
    },
    {
      name: "presupuesto",
      icon: "an-icon--euro-cable",
      heading: {
        title: "TU AHORRO EMPIEZA AQUÍ",
        subtitle: "Disfruta de un calor homogéneo y una temperatura constante en el agua caliente gracias a un suministro sin interrupciones que no ocupa espacio de almacenamiento",
      },
      active: false
    },
    {
      name: "presupuesto-realizado",
      heading: {
        title: "PRESUPUESTO REALIZADO",
        subtitle: "Rellena los datos para recibir la oferta detallada, te llamamos o te la mandamos por email",
      },
      active: false
    },
  ],
  currentStep: "cobertura",
  autocompData: {
    postalCodes: [],
    municipalities: [],
    addresses: [],
    estates: [],
    properties: [],
  },
  coverageError: "",
})

const setPostalCode = function(payload) {
  state.postalCode = payload
}

const setHouseType = function(payload) {
  console.log("house.js", payload)
  state.houseType = payload
}

const getPostalCodes = function () {
  coverageService.getPostalCodes().then((res) => {
    state.autocompData.postalCodes = xmlToJsonImp(res);
  }).catch((err) => {
    console.error(err);
  })
}

const getMunicipalities = function(pc) {
  coverageService.getMunicipalities(pc).then((res) => {
    state.autocompData.municipalities = [xmlToJsonImp(res)];
  }).catch((err) => {
    console.error(err);
  })
}

const getAddresses = function(municipalityId, postalCode) {
  coverageService.getAddresses(municipalityId, postalCode).then((res) => {
    const result = xmlToJsonImp(res)
    if(result.length) {
      state.autocompData.addresses = result
    } else {
      state.autocompData.addresses = []
    }
  }).catch((err) => {
    console.error(err);
  })
}

const getEstates = function(municipalityId, postalCode, addressKind, addressName) {
  coverageService.getEstates(municipalityId, postalCode, addressKind, addressName).then((res) => {
    const result = xmlToJsonImp(res)
    state.autocompData.estates = result
  }).catch((err) => {
    console.error(err);
  })
}

const getProperties = function(gateId) {
  coverageService.getProperties(gateId).then((res) => {
    const result = xmlToJsonImp(res)
    state.autocompData.properties = [result]
    
  }).catch((err) => {
    console.error(err);
  })
}

const changeStep = function (step) {
  const stepToChange = state.houseSteps.find((homeStep) => homeStep.name === step)
  state.houseSteps.forEach((homeStep) => homeStep.active = false)
  stepToChange.active = true
  state.currentStep = step;
}

const setCoverageError = function(msg) {
  state.coverageError = msg
}

const submitUserContactInfo = function (budgetReadyForm) {
  const { 
    name,
    lastname,
    phone,
    email,
    privacyPolicy,
    offersAndServices } = budgetReadyForm;

    console.log("budgetReadyForm", budgetReadyForm);
}

export default { 
  state: shallowReadonly(state), 
  setPostalCode,
  setHouseType,
  changeStep,
  submitUserContactInfo,
  getPostalCodes,
  getMunicipalities,
  getAddresses,
  getEstates,
  getProperties,
  setCoverageError,
}