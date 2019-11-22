// Maps constraint name to user-friendly facet label.
// Order of object properties determines order in sidebar.
const hubPropertiesConfig = [ 
  {
    facetName: "Collection",
    displayName: "Collection",
    tooltip: "A name defining a group of documents."
  },
  /* {
    facetName: "createdOnRange",
    displayName: "Created On",
    tooltip: "The date when a document was harmonized."
  }, */
  {
    facetName: "createdInFlowRange",
    displayName: "Flow",
    tooltip: "A sequence of one or more steps that processes or enhances the data."
  },
  {
    facetName: "createdByStep",
    displayName: "Step",
    tooltip: "Code that processes or enhances the data."
  },
]

export default hubPropertiesConfig;