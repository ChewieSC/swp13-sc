package de.uni_leipzig.informatik.swp13_sc.ui;

import virtuoso.jena.driver.VirtGraph;

import com.hp.hpl.jena.query.ResultSet;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.uni_leipzig.informatik.swp13_sc.sparql.QuerySearch;
import de.uni_leipzig.informatik.swp13_sc.sparql.SimpleSearch;

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
    private FormLayout exSearchLayout;

    /** Suchfelder fuer erweiterte Suche */
    private FieldGroup exSearchFields;

    private TextField tf_Name_1; // name player 1
    private TextField tf_Name_2; // name player 2
    private ComboBox cb_Color_1; // color of player 1
    private ComboBox cb_Color_2; // color of player 2
    private ComboBox cb_ResultType; // player or game

    private TextField tf_Date; // date of game
    private TextField tf_Event; // event of game
    private TextField tf_Site; // site of game
    private TextField tf_Round; // round of game
    private ComboBox cb_Result; // result of game - 1-0, 0-1, 1/2-1/2

    private final static String SL_GAME = "Game";
    private final static String SL_PLAYER1 = "Player 1";
    private final static String SL_PLAYER2 = "Player 2";
    private final static String SL_NOCOLOR = "black or white";
    private final static String SL_WHITE = "white";
    private final static String SL_BLACK = "black";

    /** beendet simpleSearch */
    private Button btnEndSearch;

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

    public void initSearch()
    {
        simpleSearchLayoutInner = new HorizontalLayout();
        simpleSearchLayoutInner.setSizeFull();
        simpleSearchLayoutInner.setWidth("100%");

        btnSearch = new Button("Suche Starten");
        btnEndSearch = new Button("Zurück");

        btnSearch.addClickListener(new ClickListener()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                SimpleSearch ss = new SimpleSearch();
                ss.setDBConnection(new VirtGraph(
                        "http://localhost:1358/millionbase",
                        "jdbc:virtuoso://pcai042.informatik.uni-leipzig.de:1357",
                        "dba", "dba"));

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
                
                if (resultTable != null)
                {
                    removeComponent(resultTable);
                }
                resultTable = new ResultTable(ss.getResult());
                addComponent(resultTable);
                activeResults = true;
                
                Notification.show("Number of results",
                        "Total: " + ss.getResultCount(),
                        Notification.Type.TRAY_NOTIFICATION);

                btnSearch.setEnabled(false);
            }
        });

        btnEndSearch.addClickListener(new ClickListener()
        {
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
                    removeComponent(exSearchLayout);
                    removeComponent(simpleSearchLayoutInner);
                    btnOpenSearch.setEnabled(true);

                    removeComponent(btnEndSearch);
                }
            }
        });

        simpleSearchLayoutInner.addComponent(btnSearch);
        addComponent(btnEndSearch);

        exSearchLayout = new FormLayout();
        exSearchFields = new FieldGroup();

        tf_Date = new TextField("Date");
        tf_Event = new TextField("Event");
        tf_Name_1 = new TextField("Name of Player 1");
        tf_Name_2 = new TextField("Name of Player 2");
        tf_Round = new TextField("Round");
        tf_Site = new TextField("Site");

        cb_Result = new ComboBox("Result of Game");
        cb_Result.setNullSelectionAllowed(true);
        cb_Result.setNullSelectionItemId("- choose -"); // default
        cb_Result.addItem(SimpleSearch.FIELD_VALUE_CG_RESULT_BLACK);
        cb_Result.addItem(SimpleSearch.FIELD_VALUE_CG_RESULT_DRAW);
        cb_Result.addItem(SimpleSearch.FIELD_VALUE_CG_RESULT_WHITE);

        cb_ResultType = new ComboBox("Search for");
        cb_ResultType.setNullSelectionAllowed(false); // nothing will not be
                                                      // displayed ... still not
                                                      // working
        Object obj = cb_ResultType.addItem(SL_GAME); // default
        // Notification.show("Debug obj ret", (obj == null)? "null" :
        // obj.getClass().toString(), Notification.Type.TRAY_NOTIFICATION);
        cb_ResultType.addItem(SL_PLAYER1);
        cb_ResultType.addItem(SL_PLAYER2);
        cb_ResultType.setValue(obj);

        cb_Color_1 = new ComboBox("Color of Player 1");
        cb_Color_1.setNullSelectionAllowed(false);
        obj = cb_Color_1.addItem(SL_NOCOLOR);
        cb_Color_1.addItem(SL_WHITE);
        cb_Color_1.addItem(SL_BLACK);
        cb_Color_1.select(obj); // ? want to select the first ... :(

        cb_Color_2 = new ComboBox("Color of Player 2");
        cb_Color_2.setNullSelectionAllowed(false);
        obj = cb_Color_2.addItem(SL_NOCOLOR);
        cb_Color_2.addItem(SL_WHITE);
        cb_Color_2.addItem(SL_BLACK);
        cb_Color_2.setValue(obj); // ?

        exSearchLayout.addComponent(tf_Date);
        exSearchLayout.addComponent(tf_Event);
        exSearchLayout.addComponent(tf_Round);
        exSearchLayout.addComponent(tf_Site);
        exSearchLayout.addComponent(cb_Result);

        exSearchLayout.addComponent(tf_Name_1);
        exSearchLayout.addComponent(cb_Color_1);
        exSearchLayout.addComponent(tf_Name_2);
        exSearchLayout.addComponent(cb_Color_2);

        exSearchLayout.addComponent(cb_ResultType);

        exSearchLayout.addComponent(btnSearch);
        exSearchFields.setBuffered(false);

        addComponent(exSearchLayout);
        addComponent(simpleSearchLayoutInner);
    }

    public void initQuerySearch()
    {
        qLayoutInner = new HorizontalLayout();
        qLayoutInner.setSizeFull();
        qLayoutInner.setWidth("100%");

        lblQSearch = new Label("SPARQL Abfrage für Fortgeschrittene Benutzer");

        taQuery = new TextArea();
        taQuery.setWidth("100%");

        btnEndQSearch = new Button("Zurück");
        btnEndQSearch.addClickListener(new ClickListener()
        {
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
        btnQSearch.addClickListener(new ClickListener()
        {
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
        btnOpenSearch.addClickListener(new ClickListener()
        {
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
        btnOpenQuerySearch.addClickListener(new ClickListener()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event)
            {
                if (simpleSearchActive)
                {
                    removeComponent(exSearchLayout);
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
