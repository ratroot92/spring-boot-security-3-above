import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
  OnDestroy,
} from "@angular/core";
import { CsbPanelService } from "../services/csb-panel.service";
import { WellHandoverService } from "../../../../handover-list/src/lib/services/well-handover.service";
import { SelectItem } from "primeng/components/common/selectitem";
import { WellhandoverTabsService } from "../../../../../libs/handover-list/src/lib/services/wellhandover-tabs.service";
import { ToastrsService } from "libs/handover-list/src/lib/services/toastrs.service";
import { SessionService } from "@webframework/client-core";
import { NewService } from "apps/dashboard/src/app/new.service";
import { TranslateService } from "@ngx-translate/core";
import { DatePipe } from "@angular/common";
import * as moment from "moment";
import {
  debounce,
  debounceTime,
  distinctUntilChanged,
  map,
  switchMap,
} from "rxjs/operators";
import { Table } from "primeng/table";
import { FttoMPipe } from "../pipes/fttom.pipe";
import { pipe, Subject, Subscription } from "rxjs";
import { IServicePost } from "../models/service-post";
import { IExportPost } from "../models/export-post";

interface IColumn {
  columnName: string;
  columnHeaderName: string;
  showColumn: boolean;
  field_name: string;
  columnType: string;
  ENT_META_DETAILS_S?: any;
  controlType?: string;
}

@Component({
  selector: "wellhandover-csb-panel",
  templateUrl: "./csb-panel.component.html",
  styleUrls: ["./csb-panel.component.scss"],
})
export class CsbPanelComponent implements OnInit, AfterViewInit, OnDestroy {
  hasAccess = false;
  userInfo;
  currentUserSubscription;
  switchLoadingScreen = false;
  urlSubHit = false;
  senderAreasForFilter: any[] = [];
  recipientAreasForFilter: any[] = [];
  data: any[] = [];
  @ViewChild("grid", { static: false }) grid: Table;
  minDateCreation;
  maxDateCreation;
  minDateLastUpdate;
  maxDateLastUpdate;
  rangeCreationDate; // has values of upper and lower creationdates
  rangeLastUpdateDate; // has values of upper and lower updatedate
  senderAreaData: any[];
  recAreaData: any[];
  businessArea: any[];
  filteredCompanyData: any[];
  selectedArea: any;
  businessAreaID;
  any;
  selectedFields: any[];

  // gridColumns: IColumn[] = []
  gridColumns: IColumn[] = [
    {
      columnName: "documentId",
      columnHeaderName: "Document Id",
      showColumn: true,
      field_name: "DOCUMENT_S",
      columnType: "fixed",
    },
    {
      columnName: "company_name",
      columnHeaderName: "Company Name",
      showColumn: true,
      field_name: "COMPANY_NAME",
      columnType: "fixed",
    },
    {
      columnName: "field_name",
      columnHeaderName: "Field Name",
      showColumn: true,
      field_name: "FIELD",
      columnType: "fixed",
    },
    {
      columnName: "well_name",
      columnHeaderName: "Well Name",
      showColumn: true,
      field_name: "WELL_NAME",
      columnType: "fixed",
    },
    {
      columnName: "creationDate",
      columnHeaderName: "Event",
      showColumn: true,
      field_name: "EVENT_INFO",
      columnType: "fixed",
    },
    {
      columnName: "barriertype",
      columnHeaderName: "Barrier Type",
      showColumn: true,
      field_name: "Barrier_Type",
      columnType: "fixed",
    },
    {
      columnName: "elementtype",
      columnHeaderName: "Element Type",
      showColumn: true,
      field_name: "Element_Type",
      columnType: "fixed",
    },
    {
      columnName: "element",
      columnHeaderName: "Element",
      showColumn: true,
      field_name: "Element",
      columnType: "fixed",
    },
    {
      columnName: "integrity",
      columnHeaderName: "Integrity",
      showColumn: true,
      field_name: "Integrity",
      columnType: "fixed",
    },
    {
      columnName: "top",
      columnHeaderName: "Top MD (m)",
      showColumn: true,
      field_name: "Top",
      columnType: "fixed",
    },
    {
      columnName: "base",
      columnHeaderName: "Base MD (m)",
      showColumn: true,
      field_name: "Base",
      columnType: "fixed",
    },
  ];
  dynamicColumns: IColumn[] = [];
  loading: boolean;
  generalQueryForSenderAreas = "";
  filterQuery: string = "";
  allTab = false;
  defaultQueryForTab: any;
  localizedData: any;
  dateTimeFormat: any;
  allActiveDocuments: any[] = [];
  versions: any[] = [];
  totalRecords: any;
  first = 0;
  last = 0;
  selectedBusinessArea = "";
  currentBusinesArea: string;
  cities: any;
  selectedFieldName: any[] = [];
  generalFieldsData: SelectItem[];
  versionIDs: any[];
  entIds: any[] = [];
  entFiled: void[];
  entData: void[];
  exelData: any;
  field_object: { name: any; id: any; type: any };
  field_ids: any[] = [];
  selectedDate: string;
  selectedFromDate: string;
  selectedEndDate: string;
  wellBarrieer: any[] = [];
  defaultEndDate: string;
  startDateQuery = "";
  endDateQuery = "";
  _tableData: any[] = [];
  eventDateEqualSelected: string = "";
  equalDateQuery: string = "";
  eventDateEqual = "";
  firstEvent = {};
  tableData = [];
  ftToMt: FttoMPipe;
  subscription: Subscription;
  subscription1: Subscription;
  subscription3: Subscription;
  forExcel: boolean = false;
  sendNextFilterSub$ = new Subject<any>();

  _postData: IServicePost;
  staticFilters = {};
  startDate: any;
  endDate: any;
  isBusinessAreaSelected: boolean = false;
  rows: any = 4;
  csbWellBarrierFieldId: any;
  tempId: any[] = [];

  constructor(
    private csbPanelService: CsbPanelService,
    readonly wellHandoverDataService: WellHandoverService,
    private readonly tabsService: WellhandoverTabsService,
    private toastr: ToastrsService,
    readonly sessionService: SessionService,
    public newService: NewService,
    public language: TranslateService,
    private datePipe: DatePipe
  ) {
    this.ftToMt = new FttoMPipe();
    Date.prototype["getEDMFormat"] = function () {
      return `${this.getFullYear()}-${(
        "0" + (this.getMonth() + 1).toString()
      ).slice(-2)}-${("0" + this.getDate().toString()).slice(-2)}T00:00:00.000`;
    };

    this.sendNextFilterSub$
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe((v: any) => {
        const e = {
          filters: {
            static: {
              matchMode: "input",
              value: {
                col: v.col,
                value: v.val,
              },
            },
          },
          first: 0,
          rows: this.rows,
        };
        console.log("called from constructor");

        this.onLazyLoad(e, null);
      });
  }

  ngOnInit() {
    this.defaultEndDate = this.getDefaultEndDate()["getEDMFormat"]();
    console.log("ngOnInit");
    this.getAllActiveBusinessAreas();
    this.getUserInfo();
  }

  ngOnDestroy() {
    // this.subscription.unsubscribe();
    // this.subscription1.unsubscribe();
  }
  ngAfterViewInit(): void {}

  get postData() {
    return this._postData;
  }

  set postData(e: any) {
    // // 21901
    // if(this.csbWellBarrierFieldId === undefined) {
    //   this.csbWellBarrierFieldId = 21901;
    // }
    // else {
    //   this.csbWellBarrierFieldId = this.csbWellBarrierFieldId;
    // }
    if (e.filters.static) {
      this.staticFilters[e.filters.static.value.col.toLowerCase()] =
        e.filters.static.value.value.trim();
    }
    if (e.sortField && e.sortOrder) {
      this._postData = {
        skip: e.first,
        limit: e.rows,
        dynamicCols: [],
        dynamicFilters: {},
        staticFilters: { ...this.staticFilters },
        sort: { sortField: e.sortField.toLowerCase(), sortOrder: e.sortOrder },
        date_to: "",
        date_from: "",
        fieldIds: [this.csbWellBarrierFieldId],
        sender_area_s: this.currentBusinesArea,
      };
    } else {
      this._postData = {
        skip: e.first,
        limit: e.rows,
        dynamicCols: [],
        dynamicFilters: {},
        staticFilters: { ...this.staticFilters },
        sort: {},
        date_to: "",
        date_from: "",
        fieldIds: [this.csbWellBarrierFieldId],
        sender_area_s: this.currentBusinesArea,
      };
    }
  }

  getUserInfo() {
    this.currentUserSubscription = this.sessionService
      .getCurrentUser()
      .subscribe((usr) => {
        this.userInfo = usr;
        // checking if user is admin
        if (this.userInfo.roles.filter((x) => x === "dsis-users").length > 0) {
          this.hasAccess = true;
        } else {
          this.hasAccess = false;
        }
      });
  }

  getAllActiveBusinessAreas() {
    try {
      this.wellHandoverDataService
        .getAllActiveBusinessAreas()
        .subscribe((busAreas: any) => {
          this.businessArea = busAreas;
          this.selectedBusinessArea = this.businessArea[0].AREA;
          this.businessAreaID = this.businessArea[0].BUSINESS_AREA_S;
          this.defaultQueryForTab = `SENDER_AREA_S eq '${this.businessArea[0].BUSINESS_AREA_S}' `;
          this.csbPanelService
            .getFieldIdsBusinessArea(this.businessArea[0].BUSINESS_AREA_S)
            .subscribe((controlData: any) => {
              let getControlData = controlData.data;
              getControlData.map((crType) => {
                if (crType.CONTROL_TYPE === "wellbarrier") {
                  this.csbWellBarrierFieldId = crType.ENT_META_DETAIL_S;
                  const filterData = {
                    filters: {},
                    first: 0,
                    globalFilter: null,
                    multiSortMeta: undefined,
                    sortField: undefined,
                    rows: this.rows,
                    sortOrder: 1,
                  };
                  console.log("called from getAllActiveBusinessAreas");
                  this.onLazyLoad(filterData, this.businessAreaID);
                }
              });
            });
          // this.onLazyLoad1(this.firstEvent)
        });
    } catch (err) {
      console.error("getAllActiveBusinessAreas: ", err);
    }
  }

  businesAreadSelect(e) {
    if (e && e.value && e.value.AREA) {
      this.businessAreaID = e.value.BUSINESS_AREA_S.toString();
      this.csbPanelService
        .getFieldIdsBusinessArea(this.businessAreaID)
        .subscribe((controlData: any) => {
          let getControlData = controlData.data;
          getControlData.map((crType) => {
            if (crType.CONTROL_TYPE === "wellbarrier") {
              this.csbWellBarrierFieldId = crType.ENT_META_DETAIL_S;
            }
          });
          const filterData = {
            filters: {},
            first: 0,
            globalFilter: null,
            multiSortMeta: undefined,
            sortField: undefined,
            rows: this.rows,
            sortOrder: 1,
          };
          this.onLazyLoad(filterData, this.businessAreaID);
        });
    }
  }

  clearStartDateFilter(e) {
    // if (this.postData['date_from']) delete this.postData['date_from'];
    if (this.postData["date_from"]) {
      this.postData.date_from = "";
      this.makeCallWithDynamicFilters(null);
    }
  }
  clearEndDateFilter() {
    // if (this.postData['date_to']) delete this.postData['date_to'];
    if (this.postData["date_to"]) {
      this.postData.date_to = "";
      this.makeCallWithDynamicFilters(null);
    }
  }
  onLazyLoad(e, businessAreaID) {
    if (businessAreaID) {
      this.currentBusinesArea = businessAreaID;
    } else {
      this.currentBusinesArea = this.businessAreaID;
    }

    this.postData = e;
    if (e.filters.intervention_start_dates) {
      const startDate = e.filters.intervention_start_dates.value;
      this.postData["date_from"] = `${startDate.getFullYear()}-${(
        "0" + (startDate.getMonth() + 1).toString()
      ).slice(-2)}-${("0" + startDate.getDate().toString()).slice(
        -2
      )}T00:00:00.000`;
      this.startDate = startDate;
    }
    if (e.filters.intervention_end_dates) {
      const endDate = e.filters.intervention_end_dates.value;
      this.postData["date_to"] = `${endDate.getFullYear()}-${(
        "0" + (endDate.getMonth() + 1).toString()
      ).slice(-2)}-${("0" + endDate.getDate().toString()).slice(
        -2
      )}T00:00:00.000`;
      this.endDate = endDate;
    } else {
      this.postData["date_to"] = "";
    }

    this.first = e.first + 1;
    this.last = e.first + e.rows;
    this.makeCallWithDynamicFilters(this.currentBusinesArea);
  }

  makeCallWithDynamicFilters(businessAreaID) {
    this.loading = true;
    this.postData["BUSINESS_AREA_S"] = businessAreaID;
    this.csbPanelService.getDynamicFilters(this.postData).subscribe(
      (data) => {
        this.totalRecords = data["count"];
        if (this.totalRecords) {
          this.data = data["records"].map((i) => {
            return {
              documentId: i["document_s"],
              company_name: i["company_name"],
              versionID: i["version_s"],
              well_name: i["well_name"],
              field_name: i["field"],
              creationDate: i["event_info"],
              barriertype: i["barriertype"],
              elementtype: i["elementType"],
              element: i["element"],
              integrity: i["integrity"],
              top: i["top"],
              base: i["base"],
            };
          });
          // this.data.forEach(doc => {
          //   this.dynamicCols.forEach(dC => {
          //     doc[dC] = data['records'].filter(d => d.document_s == doc.documentId)[0][dC]
          //     doc[825729] = data['records'].filter(d => d.document_s == doc.documentId)[0]['shared_csb']
          //   })
          // })
        } else {
          this.data = [];
        }
        this.loading = false;
      },
      (err) => {
        console.log(`we got problems`);
      }
    );
  }

  onLazyLoad1(e) {
    setTimeout(() => {
      if (this.businessAreaID) {
        try {
          this.postData = e;
          if (e.filters.intervention_start_dates) {
            const startDate = e.filters.intervention_start_dates.value;
            this.postData["date_from"] = `${startDate.getFullYear()}-${(
              "0" + (startDate.getMonth() + 1).toString()
            ).slice(-2)}-${("0" + startDate.getDate().toString()).slice(
              -2
            )}T00:00:00.000`;
            this.startDate = startDate;
          }
          if (e.filters.intervention_end_dates) {
            const endDate = e.filters.intervention_end_dates.value;
            this.postData["date_to"] = `${endDate.getFullYear()}-${(
              "0" + (endDate.getMonth() + 1).toString()
            ).slice(-2)}-${("0" + endDate.getDate().toString()).slice(
              -2
            )}T00:00:00.000`;
            this.endDate = endDate;
          } else {
            this.postData["date_to"] = "";
          }

          this.first = e.first + 1;
          this.last = e.first + e.rows;
          this.firstEvent = e;
          this.currentBusinesArea = `SENDER_AREA_S eq '${this.businessAreaID}' `;
          this.loading = true;

          this.filterQuery = "";
          this.setFilterQuery(e);
          this.subscription = this.csbPanelService
            .getData(
              {
                sortOrder: e.sortOrder,
                sortField: e.sortField,
                rows: e.rows,
                first: e.first,
                filterString:
                  (this.businessAreaID
                    ? this.currentBusinesArea
                    : this.defaultQueryForTab) +
                  this.filterQuery +
                  this.startDateQuery +
                  this.endDateQuery +
                  this.equalDateQuery,
              },
              this.forExcel
            )
            .subscribe(
              (res) => {
                this._tableData = [];
                if (
                  res["dataReturned"] != undefined &&
                  res["dataReturned"] != null
                ) {
                  this.data = res["dataReturned"].map((i) => {
                    return {
                      doc_id: i.DOCUMENT_S,
                      well_name: i.WELL_NAME,
                      field_name: i.FIELD,
                      company_name: i.COMPANY_NAME,
                      creationDate: i.EVENT_INFO,
                      versionID: i.VERSION_S,
                      fieldData: null, //[el1, el2,el3,el4]
                      // lastUpdate: this.convertUTCToLocal(i.VER_LAST_UPDATED_DATE),
                    };
                  });
                }

                this.versionIDs = this.data.map((e) => e.versionID);
                const documentsFieldsIDs = {
                  version_ids: this.versionIDs,
                  field_ids: [21901],
                };
                this.subscription1 = this.csbPanelService
                  .generalDocumentsFieldsData(documentsFieldsIDs)
                  .subscribe(
                    (val: any[]) => {
                      this.data = this.data.map((t1) => ({
                        ...t1,
                        ...val.find((t2) => t2.versionID === t1.versionID),
                      }));
                      this.data.forEach((row) => {
                        let rowObj: any = {};
                        rowObj.doc_id = row.doc_id;
                        rowObj.versionID = row.versionID;
                        rowObj.company_name = row.company_name;
                        rowObj.field_name = row.field_name;
                        rowObj.well_name = row.well_name;
                        rowObj.creationDate = row.creationDate;
                        row.fieldData.forEach((fieldData) => {
                          if (fieldData != null) {
                            let barrierObj: any = {};
                            barrierObj.barriertype = fieldData
                              ? fieldData.parentBarrier
                              : "-";
                            barrierObj.elementType = fieldData
                              ? this.getTypeLabel(fieldData.option)
                              : "-";
                            barrierObj.element = fieldData
                              ? fieldData.label
                              : "-";
                            barrierObj.integrity = fieldData
                              ? this.getIntegrityTitle(
                                  fieldData.integrity_test.status
                                )
                              : "-";
                            barrierObj.top = fieldData
                              ? this.getBarrierInfo(fieldData, "top")
                              : "-";
                            barrierObj.base = fieldData
                              ? this.getBarrierInfo(fieldData, "base")
                              : "-";
                            this._tableData.push({ ...rowObj, ...barrierObj });
                          }
                        });
                      });
                      this.loading = false;
                      this.handleLanguage();
                      this.totalRecords = res["count"];
                      this.first = this.totalRecords > 0 ? e.first + 1 : 0;
                      this.last =
                        e.first + e.rows < this.totalRecords
                          ? e.first + e.rows
                          : this.totalRecords;
                    },
                    (err) => {
                      console.log(err);
                    }
                  );
                //
              },
              (err) => {
                console.error("onLazyLoad(): getData(): ", err);
                this.toastr.showError(err.message);
              }
            );
        } catch (err) {
          // console.error('onLazyLoad(): Internal error occurred in system.', err);
          // this.toastr.showError(err.message);
        }
      }
    }, 1500);
  }

  getIntegrityTitle(status: any) {
    if (status) {
      switch (status) {
        case "Valid":
          return "Compliance";
          break;
        case "Invalid":
          return "Not Compliance";
          break;
        case "graded":
          return "Degraded";
          break;

        default:
          return "None";
          break;
      }
    } else {
      return "-";
    }
  }

  setFilterQuery(e: any) {
    if (e.filters) {
      let dateQuery = "";

      if (e.filters["DOCUMENT_S"]) {
        this.filterQuery =
          this.filterQuery +
          "and substringof('" +
          e.filters["DOCUMENT_S"].value.toUpperCase() +
          "', toupper(DOCUMENT_S)) eq true ";
      }
      if (e.filters["WELL_NAME"]) {
        this.filterQuery =
          this.filterQuery +
          "and substringof('" +
          e.filters["WELL_NAME"].value.toUpperCase() +
          "', toupper(WELL_NAME)) eq true ";
      }
      if (e.filters["COMPANY_NAME"]) {
        this.filterQuery =
          this.filterQuery +
          "and substringof('" +
          e.filters["COMPANY_NAME"].value.toUpperCase() +
          "', COMPANY_NAME) eq true ";
      }
      if (e.filters["FIELD"]) {
        this.filterQuery =
          this.filterQuery +
          "and substringof('" +
          e.filters["FIELD"].value.toUpperCase() +
          "', toupper(FIELD)) eq true ";
      }

      if (e.filters["EVENT_DATE"]) {
        let date = e.filters.EVENT_DATE.value;
        this.eventDateEqualSelected = date["getEDMFormat"]();
        this.equalDateQuery =
          " and EVENT_DATE eq datetime'" + this.eventDateEqualSelected + "'";
        this.rangeCreationDate = "";
        this.rangeLastUpdateDate = "";
        this.startDateQuery = "";
        this.endDateQuery = "";
        delete e.filters["EVENT_DATE"];
      }
      if (e.filters["intervention_start_dates"]) {
        let date = e.filters.intervention_start_dates.value;
        this.selectedFromDate = date["getEDMFormat"]();
        this.startDateQuery =
          " and EVENT_DATE ge datetime'" + this.selectedFromDate + "'";
        delete e.filters["intervention_start_dates"];
        delete e.filters["EVENT_DATE"];
        this.equalDateQuery = "";
      }
      if (e.filters["intervention_end_dates"]) {
        let date = e.filters.intervention_end_dates.value;
        this.selectedEndDate = date["getEDMFormat"]();
        this.endDateQuery =
          " and EVENT_DATE le datetime'" + this.selectedEndDate + "'";
        delete e.filters["intervention_end_dates"];
        delete e.filters["EVENT_DATE"];
        this.equalDateQuery = "";
      }
      if (e.filters["clear_end_dates"]) {
        this.endDateQuery = "";
        delete e.filters["clear_end_dates"];
      }
      if (e.filters["clear_start_dates"]) {
        this.startDateQuery = "";
        delete e.filters["clear_start_dates"];
      }
      if (e.filters["clear_equal_date"]) {
        this.equalDateQuery = "";
        delete e.filters["clear_equal_date"];
      }
    } else {
      this.loading = false;
      return;
    }
  }

  getDefaultEndDate = (date = new Date()) => {
    date.setDate(date.getDate() + 1);
    return date;
  };
  defaultStartDate = "1900-01-01T00:00:00.000";

  renameColumns() {
    this.gridColumns[0].columnHeaderName = this.gridColumns[0].columnHeaderName;
    this.gridColumns[1].columnHeaderName = this.localizedData.COMPANY_NAME;
    this.gridColumns[2].columnHeaderName = this.localizedData.FIELD_NAME;
    this.gridColumns[3].columnHeaderName = this.localizedData.WELL_NAME;
    this.gridColumns[4].columnHeaderName = this.localizedData.EVENT;
  }

  handleLanguage() {
    this.newService.currentLanguage.subscribe((val: string) => {
      this.language.use(val).subscribe(() => {
        if (this.language.store.translations[val]) {
          this.localizedData =
            this.language.store.translations[val]["WELLHANDOVER-GRID"];
          this.dateTimeFormat = this.language.store.translations[val]["APP"];
          if (this.rangeCreationDate) {
            this.rangeCreationDate = moment(
              this.rangeCreationDate,
              this.language.store.translations[
                this.newService.getPreviousLanguage()
              ]["APP"].DATE_FORMAT.toUpperCase()
            ).format(
              this.language.store.translations[val][
                "APP"
              ].DATE_FORMAT.toUpperCase()
            );
          }
          if (this.rangeLastUpdateDate) {
            this.rangeLastUpdateDate = moment(
              this.rangeLastUpdateDate,
              this.language.store.translations[
                this.newService.getPreviousLanguage()
              ]["APP"].DATE_FORMAT.toUpperCase()
            ).format(
              this.language.store.translations[val][
                "APP"
              ].DATE_FORMAT.toUpperCase()
            );
          }
          // this.renameColumns();
        } else {
          this.toastr.showInfo(
            "Unable to complete language parsing, please refresh the page"
          );
        }
      });
    });
  }

  getBarrierInfo(element, tb) {
    if (element.option == "casingOption") {
      if (tb == "base") {
        return element.casing_to_value != null
          ? element.casing_to_value
          : "None";
      } else if (tb == "top") {
        return element.casing_from_value != null
          ? element.casing_from_value
          : "None";
      }
    } else if (element.option == "cementOption") {
      if (tb == "base") {
        return element.cement_to_value != null
          ? element.cement_to_value
          : "None";
      } else if (tb == "top") {
        return element.cement_from_value != null
          ? element.cement_from_value
          : "None";
      }
    } else if (element.option == "fluidOption") {
      if (tb == "base") {
        return element.fluid_to_value != null ? element.fluid_to_value : "None";
      } else if (tb == "top") {
        return element.fluid_from_value != null
          ? element.fluid_from_value
          : "None";
      }
    } else {
      if (tb == "base") {
        return element.baseMD != null
          ? this.ftToMt.transform(element.baseMD)
          : "None";
      } else if (tb == "top") {
        return element.topMD != null
          ? this.ftToMt.transform(element.topMD)
          : "None";
      }
    }
  }

  getTypeLabel(option) {
    if (option == "fluidOption") {
      return "Fluid";
    }
    if (option == "cementOption") {
      return "Cement";
    }
    if (option == "casingOption") {
      return "Casing";
    }
    if (option == "assemblyComponentOption") {
      return "Assembly";
    }
    if (option == "wellheadComponentOption") {
      return "Wellhead";
    }
    if (option == "lithologyOption") {
      return "Formation";
    }
  }

  // exportExcel1() {
  //   const excelObject = {
  //     "from": this.selectedFromDate ? this.selectedFromDate : this.defaultStartDate,
  //     "to": this.selectedEndDate ? this.selectedEndDate : this.defaultEndDate, //fix end date marker
  //     "businessArea": this.businessAreaID,
  //     "field_ids": this.field_ids[0] ? this.field_ids[0] : [],
  //     "email": this.userInfo.email
  //   }
  //   this.csbPanelService.getExelFileData(excelObject).subscribe(e => {

  //   })
  // }

  exportExcel() {
    this.postData.date_from;
    this.postData.data_to;
    const exportPostData: IExportPost = {
      email: this.userInfo.email,
      sender_areas: [
        {
          skip: 0,
          limit: 0,
          dynamicFilters: {},
          sort: {},
          staticFilters: { ...this.staticFilters },
          dynamicCols: [],
          date_from: this.postData.date_from ? this.startDate : "",
          date_to: this.postData.date_to ? this.endDate : "",
          fieldIds: [this.csbWellBarrierFieldId],
          sender_area_s: this.currentBusinesArea,
        },
      ],
    };
    this.toastr.showInfo(
      "Excel is in process, you'll receive an email with the file attached."
    );
    this.csbPanelService
      .getExportData({ ...exportPostData, email: this.userInfo.email })
      .subscribe((e) => {});
  }

  // saveAsExcelFile(buffer: any, fileName: string): void {
  //   import("file-saver").then(FileSaver => {
  //     let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
  //     let EXCEL_EXTENSION = '.xlsx';
  //     const data: Blob = new Blob([buffer], {
  //       type: EXCEL_TYPE
  //     });
  //     FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
  //   });
  // }
}
