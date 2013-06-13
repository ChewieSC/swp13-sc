
package de.uni_leipzig.informatik.swp13_sc.ui;

import java.util.Iterator;

import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.query.ResultSet;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.uni_leipzig.informatik.swp13_sc.sparql.QuerySearch;
import de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch;
import de.uni_leipzig.informatik.swp13_sc.ui.ResultTable;
import de.uni_leipzig.informatik.swp13_sc.util.Configuration;

/**
 * Kapselt das Layout und die Funktionalitï¿½ten des Suchfensters und dessen
 * Komponenten
 * 
 * @author LingLong
 * 
 */
public class SearchView extends VerticalLayout
{
    private static final long serialVersionUID = 1L;

    /** Inneres Layout fuer Suchfenster */
    private HorizontalLayout searchLayoutInner;

    /** Label mit Ueberschrift */
    private Label lblSearch;

    /** Button zum oeffnen der Suchfunktion */
    private Button btnOpenSearch;

    /** Button zum oeffnen der sparql-Querysuche */
    private Button btnOpenQuerySearch;

    private boolean simpleSearchActive = false;
    private boolean querySearchActive = false;

    // ----- simpleSearch Instanzen -----//

    private HorizontalLayout simpleSearchLayoutInner;

    /** Suchbutton */
    private Button btnSearch;

    /** Layout fuer erweiterte Suche */
    private VerticalLayout simpleSearchLayout;

    private FormLayout fly_cg_1;
    private FormLayout fly_cg_2;

    /** Suchfelder fuer erweiterte Suche */

    private TextField tf_Name_1; // name player 1
    private TextField tf_Name_2; // name player 2

    private TextField tf_birth_p1; // Geburt player1
    private TextField tf_birth_p2; // Geburt player2

    private TextField tf_death_p1; // Todestag player1
    private TextField tf_death_p2; // Todestag player2

    private TextField tf_nation_p1;
    private TextField tf_nation_p2;

    private TextField tf_elo_p1; // elo player 1,2
    private TextField tf_elo_p2;

    private TextField tf_birthPlace_p1;
    private TextField tf_birthPlace_p2;

    private TextField tf_peak_p1; // PeakRanking player 1,2
    private TextField tf_peak_p2;

    private ComboBox cb_Color_1; // color of player 1 
    private Object cbCol1NoCol;
    private Object cbCol1White;
    private Object cbCol1Black;
    private ComboBox cb_Color_2; // color of player 2
    private Object cbCol2NoCol;
    private Object cbCol2White;
    private Object cbCol2Black;
    private ComboBox cb_ResultType; // player or game
    private ComboBox cb_Result; // result of game - 1-0, 0-1, 1/2-1/2

    private TextField tf_Date; // date of game
    private TextField tf_Event; // event of game
    private TextField tf_Site; // site of game
    private TextField tf_Round; // round of game
    private TextField tf_ECO; // ECO/Opening of game

    private final static String SL_GAME = "Game";
    private final static String SL_PLAYER1 = "Player 1";
    private final static String SL_PLAYER2 = "Player 2";
    private final static String SL_NOCOLOR = "black or white";
    private final static String SL_WHITE = "white";
    private final static String SL_BLACK = "black";

    /** beendet simpleSearch */
    private Button btnEndSearch;
    
    private Swp13scUI ui;

    // ----- Instanzen Querysuche -----//

    private QuerySearch querySearch;

    private HorizontalLayout qLayoutInner;

    private Label lblQSearch;

    private Button btnQSearch;

    /** Textfeld zum einfï¿½gen bzw. schreiben von Queries */
    private TextArea taQuery = new TextArea();

    /** Ends QuerySearch, removes LayoutComponents */
    private Button btnEndQSearch;

    // --------Ausgabe--------//

    private boolean activeResults = false;

    private ResultTable resultTable;

    // Konstruktor
    /**
     * Hier wird ein Objekt erzeugt welches das Layout des Suchefensters und
     * seine Funktion aufbaut
     */
    public SearchView()
    {
        setSpacing(true);

        searchLayoutInner = new HorizontalLayout();
        searchLayoutInner.setSizeFull();
        searchLayoutInner.setWidth("100%");

        lblSearch = new Label("Suche nach Schachpartien");
        addComponent(lblSearch);

        btnOpenSearch = new Button("Einfache Suche");
        btnOpenQuerySearch = new Button("SPARQL-Query Suche");

        searchLayoutInner.addComponent(btnOpenSearch);
        searchLayoutInner.addComponent(btnOpenQuerySearch);

        addComponent(searchLayoutInner);
    }
    public void setUi(Swp13scUI ui)
    {
    	this.ui = ui;	
    }
    public void initSearch()
    {
        simpleSearchLayoutInner = new HorizontalLayout();
        simpleSearchLayoutInner.setSizeFull();
        simpleSearchLayoutInner.setWidth("100%");

        simpleSearchLayout = new VerticalLayout();

        btnSearch = new Button("Suche Starten");
        btnEndSearch = new Button("Zurück");

        btnSearch.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                Configuration c = Configuration.getInstance();
                SimpleSearch ss = new SimpleSearch();
//                ss.setDBConnection(new VirtGraph ("millionbase",
//                        "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357",
//                        "dba", "dba"));
                //TODO: set back on deployment
                ss.setDBConnection(new VirtGraph(c.getVirtuosoBasegraph(),
                        "jdbc:virtuoso://" + c.getVirtuosoHostname(), c
                                .getVirtuosoUsername(), c.getVirtuosoPassword()));

                String s = tf_Date.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CG_DATE, s);
                }
                s = tf_Event.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CG_EVENT, s);
                }
                s = tf_Round.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CG_ROUND, s);
                }
                s = tf_Site.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CG_SITE, s);
                }
                s = tf_ECO.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CG_ECO, s);
                }

                s = tf_Name_1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_NAME, s);
                }
                s = tf_Name_2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_NAME, s);
                }
                s = (String) cb_Color_1.getValue();
                if (s != null && !"".equals(s))
                {
                    if (SL_BLACK.equals(s))
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR,
                                SimpleSearch.FIELD_VALUE_CP_COLOR_BLACK);
                    }
                    else if (SL_WHITE.equals(s))
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR,
                                SimpleSearch.FIELD_VALUE_CP_COLOR_WHITE);
                    }
                    else
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_CP1_COLOR,
                                SimpleSearch.FIELD_VALUE_CP_COLOR_NOCOLOR);
                    }
                }
                s = (String) cb_Color_2.getValue();
                {
                    if (SL_BLACK.equals(s))
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR,
                                SimpleSearch.FIELD_VALUE_CP_COLOR_BLACK);
                    }
                    else if (SL_WHITE.equals(s))
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR,
                                SimpleSearch.FIELD_VALUE_CP_COLOR_WHITE);
                    }
                    else
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_CP2_COLOR,
                                SimpleSearch.FIELD_VALUE_CP_COLOR_NOCOLOR);
                    }
                }

                s = (String) cb_Result.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CG_RESULT, s);
                }

                s = (String) cb_ResultType.getValue();
                if (s != null && !"".equals(s))
                {
                    if (SL_PLAYER1.equals(s))
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE,
                                SimpleSearch.FIELD_VALUE_RESULTTYPE_PLAYER1);
                    }
                    else if (SL_PLAYER2.equals(s))
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE,
                                SimpleSearch.FIELD_VALUE_RESULTTYPE_PLAYER2);
                    }
                    else
                    {
                        ss.setField(SimpleSearch.FIELD_KEY_RESULTTYPE,
                                SimpleSearch.FIELD_VALUE_RESULTTYPE_GAME);
                    }
                }

                // -----------Ergänzungen simpleSearch---------//

                s = tf_birth_p1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_BIRTH_DATE, s);
                }

                s = tf_birth_p2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_BIRTH_DATE, s);
                }

                s = tf_death_p1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_DEATH_DATE, s);
                }

                s = tf_death_p2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_DEATH_DATE, s);
                }

                s = tf_nation_p1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_NATION, s);
                }

                s = tf_nation_p2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_NATION, s);
                }

                s = tf_elo_p1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_ELO, s);
                }

                s = tf_elo_p2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_ELO, s);
                }

                s = tf_birthPlace_p1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_BIRTH_PLACE, s);
                }

                s = tf_birthPlace_p2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_BIRTH_PLACE, s);
                }

                s = tf_peak_p1.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP1_PEAK_RANKING, s);
                }

                s = tf_peak_p2.getValue();
                if (s != null && !"".equals(s))
                {
                    ss.setField(SimpleSearch.FIELD_KEY_CP2_PEAK_RANKING, s);
                }

                // -------------------------------------------//
                
                System.out.println(ss.getSPARQLQuery());

                if (resultTable != null)
                {
                    removeComponent(resultTable);
                }
                if(cb_ResultType.getValue() == null || cb_ResultType.getValue().equals(SL_GAME))
                	resultTable = new ResultTable(ss.getResult(),ui);
                else
                	resultTable = new ResultTable(ss.getResult());
                addComponent(resultTable);
                activeResults = true;

                Notification.show("Number of results",
                        "Total: " + ss.getResultCount(),
                        Notification.Type.TRAY_NOTIFICATION);

                btnSearch.setEnabled(false);
            }
        });

        btnEndSearch.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                if (activeResults)
                {
                    removeComponent(resultTable);
                    resultTable = null;
                    activeResults = false;

                    btnSearch.setEnabled(true);
                }
                else
                {
                    removeComponent(simpleSearchLayout);
                    removeComponent(simpleSearchLayoutInner);
                    btnOpenSearch.setEnabled(true);

                    removeComponent(btnEndSearch);
                }
            }
        });

        addComponent(btnEndSearch);

        tf_Date = new TextField("Date");
        tf_Event = new TextField("Event");
        tf_Round = new TextField("Round");
        tf_Site = new TextField("Site");
        tf_ECO = new TextField("ECO (Opening)");
        //tf_ECO.setMaxLength(3); // now combined (code and name)

        cb_Result = new ComboBox("Result of Game");
        cb_Result.setNullSelectionAllowed(true);
        cb_Result.setNullSelectionItemId("- choose -"); // default
        cb_Result.addItem(SimpleSearch.FIELD_VALUE_CG_RESULT_BLACK);
        cb_Result.addItem(SimpleSearch.FIELD_VALUE_CG_RESULT_DRAW);
        cb_Result.addItem(SimpleSearch.FIELD_VALUE_CG_RESULT_WHITE);

        fly_cg_1 = new FormLayout(tf_Event, tf_Site, tf_Date);
        fly_cg_1.setMargin(true);
        
        fly_cg_2 = new FormLayout(tf_Round, cb_Result, tf_ECO);
        fly_cg_2.setMargin(true);
        
        HorizontalLayout hly_cg = new HorizontalLayout(fly_cg_1, fly_cg_2);
        hly_cg.setSpacing(true);

        Panel pnl_cg = new Panel();
        pnl_cg.setCaption("Chess game");
        pnl_cg.setContent(hly_cg);
        

        cb_ResultType = new ComboBox("Search for");
        cb_ResultType.setNullSelectionAllowed(false);
        Object obj = cb_ResultType.addItem(SL_GAME); // default
        // Notification.show("Debug obj ret", (obj == null)? "null" :
        // obj.getClass().toString(), Notification.Type.TRAY_NOTIFICATION);
        // TODO: maybe one day there could be a click button for player details as well 
//        cb_ResultType.addItem(SL_PLAYER1);
//        cb_ResultType.addItem(SL_PLAYER2);
        cb_ResultType.setValue(obj);

        tf_Name_1 = new TextField("Name");
        tf_Name_2 = new TextField("Name");

        cb_Color_1 = new ComboBox("Color of Player");
        cb_Color_1.setNullSelectionAllowed(false);
        cb_Color_1.setImmediate(true);
        
        cbCol1NoCol = cb_Color_1.addItem(SL_NOCOLOR);
        cbCol1White =  cb_Color_1.addItem(SL_WHITE);
        cbCol1Black =  cb_Color_1.addItem(SL_BLACK);
        
        cb_Color_2 = new ComboBox("Color of Player");
        cb_Color_2.setNullSelectionAllowed(false);
        cb_Color_2.setImmediate(true);
        cbCol2NoCol = cb_Color_2.addItem(SL_NOCOLOR);
        cbCol2White = cb_Color_2.addItem(SL_WHITE);
        cbCol2Black = cb_Color_2.addItem(SL_BLACK);
        
        cb_Color_1.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object id = event.getProperty();
				Iterator iter = cb_Color_2.getItemIds().iterator();
			//	System.out.println("ID: "+cb_Color_1.getItem(id).toString()+ " EventID: "+event.getProperty());
				while(iter.hasNext())
				{
					Object cb2Id = iter.next();
					if(event.getProperty().getValue().equals(cb2Id.toString()))
					{
						if(cb2Id.toString().equals(SL_NOCOLOR))
						{	
							cb_Color_2.setValue(cb2Id);
						}
						else if(cb2Id.toString().equals(SL_WHITE))
						{
							cb_Color_2.setValue(iter.next());
						}
						else if(cb2Id.toString().equals(SL_BLACK))
						{
							Iterator newIt = cb_Color_2.getItemIds().iterator();
							newIt.next();
							cb_Color_2.setValue(newIt.next());
						}
						
					}
					
				}
				
			}
			
		});
        cb_Color_2.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object id = event.getProperty();
				Iterator iter = cb_Color_1.getItemIds().iterator();
			//	System.out.println("ID: "+cb_Color_1.getItem(id).toString()+ " EventID: "+event.getProperty());
				while(iter.hasNext())
				{
					Object cb2Id = iter.next();
					if(event.getProperty().getValue().equals(cb2Id.toString()))
					{
						if(cb2Id.toString().equals(SL_NOCOLOR))
						{	
							cb_Color_1.setValue(cb2Id);
						}
						else if(cb2Id.toString().equals(SL_WHITE))
						{
							cb_Color_1.setValue(iter.next());
						}
						else if(cb2Id.toString().equals(SL_BLACK))
						{
							Iterator newIt = cb_Color_1.getItemIds().iterator();
							newIt.next();
							cb_Color_1.setValue(newIt.next());
						}
						
					}
					
				}
				
			}
			
		});

        tf_birth_p1 = new TextField("Birth date");
        tf_birth_p2 = new TextField("Birth date");
        tf_death_p1 = new TextField("Date of death");
        tf_death_p2 = new TextField("Date of death");
        tf_nation_p1 = new TextField("Nationality");
        tf_nation_p2 = new TextField("Nationality");
        tf_elo_p1 = new TextField("ELO");
        tf_elo_p2 = new TextField("ELO");
        tf_birthPlace_p1 = new TextField("Birth place");
        tf_birthPlace_p2 = new TextField("Birth place");
        tf_peak_p1 = new TextField("Peak-Ranking");
        tf_peak_p2 = new TextField("Peak-Ranking");

        Panel pnl_p1 = new Panel();
        pnl_p1.setCaption("First player");
        pnl_p1.setSizeUndefined();

        FormLayout fly_p1 = new FormLayout(tf_Name_1, cb_Color_1, tf_nation_p1,
                tf_birth_p1, tf_birthPlace_p1, tf_death_p1, tf_elo_p1,
                tf_peak_p1);
        fly_p1.setSizeUndefined();
        fly_p1.setMargin(true);
        pnl_p1.setContent(fly_p1);

        Panel pnl_p2 = new Panel();
        pnl_p2.setCaption("Second player");

        FormLayout fly_p2 = new FormLayout(tf_Name_2, cb_Color_2, tf_nation_p2,
                tf_birth_p2, tf_birthPlace_p2, tf_death_p2, tf_elo_p2,
                tf_peak_p2);
        fly_p2.setMargin(true);
        pnl_p2.setContent(fly_p2);
        
        GridLayout gl = new GridLayout(2, 2);
        gl.addComponent(pnl_cg, 0, 0, 1, 0);
        gl.addComponent(pnl_p1);
        gl.addComponent(pnl_p2);
        gl.setSizeUndefined();
        gl.setSpacing(true);
        
        simpleSearchLayout.addComponent(gl);
//        simpleSearchLayout.addComponent(cb_ResultType);
        simpleSearchLayout.addComponent(btnSearch);
        simpleSearchLayout.setSpacing(true);

        addComponent(simpleSearchLayout);
    }

    public void initQuerySearch()
    {
        qLayoutInner = new HorizontalLayout();
        qLayoutInner.setSizeFull();
        qLayoutInner.setWidth("100%");

        lblQSearch = new Label("SPARQL Abfrage für Fortgeschrittene Benutzer");

        taQuery = new TextArea();
        taQuery.setWidth("75%");

        btnEndQSearch = new Button("Zurück");
        btnEndQSearch.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                if (activeResults)
                {
                    removeComponent(resultTable);
                    resultTable = null;
                    btnQSearch.setEnabled(true);
                    activeResults = false;
                }
                else
                {
                    removeComponent(lblQSearch);
                    removeComponent(taQuery);
                    removeComponent(qLayoutInner);
                    btnOpenSearch.setEnabled(true);
                    btnOpenQuerySearch.setEnabled(true);
                    querySearchActive = false;

                    removeComponent(btnEndQSearch);
                }
            }
        });

        btnQSearch = new Button("Absenden");
        btnQSearch.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                try
                {
                    querySearch = new QuerySearch(taQuery.getValue());

                    ResultSet results = querySearch.getResultSet();
                    if (resultTable != null)
                    {
                        removeComponent(resultTable);
                    }
                    resultTable = new ResultTable(results);
                    addComponent(resultTable);

                    activeResults = true;
                    btnQSearch.setEnabled(false);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Notification.show("Error!", e.getLocalizedMessage(),
                            Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        addComponent(btnEndQSearch);
        qLayoutInner.addComponent(btnQSearch);

        addComponent(lblQSearch);
        addComponent(taQuery);
        addComponent(qLayoutInner);
    }

    /** erzeugt uebergeordnette Suchfensterfunktionen */
    public void initFunktion()
    {
        // oeffne simpleSearchView
        btnOpenSearch.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                if (querySearchActive)
                {
                    if (activeResults)
                    {
                        removeComponent(resultTable);
                    }

                    removeComponent(lblQSearch);
                    removeComponent(taQuery);
                    removeComponent(qLayoutInner);

                    removeComponent(btnEndQSearch);

                    simpleSearchActive = true;
                    querySearchActive = false;

                    initSearch();

                    btnOpenSearch.setEnabled(false);
                    btnOpenQuerySearch.setEnabled(true);
                }
                else
                {
                    simpleSearchActive = true;
                    initSearch();
                    btnOpenSearch.setEnabled(false);
                }
            }
        });

        // oeffne QuerySearchView
        btnOpenQuerySearch.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                if (simpleSearchActive)
                {
                    removeComponent(simpleSearchLayout);
                    removeComponent(simpleSearchLayoutInner);
                    removeComponent(btnEndSearch);

                    btnOpenSearch.setEnabled(true);
                    btnOpenQuerySearch.setEnabled(false);

                    initQuerySearch();

                    simpleSearchActive = false;
                    querySearchActive = true;
                }
                else
                {
                    initQuerySearch();
                    btnOpenQuerySearch.setEnabled(false);
                    querySearchActive = true;
                }
            }
        });
    }
}
