package com.w3itexperts.ombe.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.w3itexperts.ombe.databinding.FragmentChartsBinding;

import java.util.ArrayList;
import java.util.List;

public class Charts extends Fragment {
    FragmentChartsBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentChartsBinding.inflate(inflater, container, false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create a list of entries
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 4.5f));
        entries.add(new Entry(1, 5.0f));
        entries.add(new Entry(2, 3.0f));
        entries.add(new Entry(3, 6.0f));
        entries.add(new Entry(4, 5.5f));
        entries.add(new Entry(5, 6.5f));
        entries.add(new Entry(6, 4.0f));
        entries.add(new Entry(7, 5.5f));

        // Create a dataset and give it a type
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setColor(Color.parseColor("#4CAF50"));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.parseColor("#4CAF50"));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        b.lineChart.setContentDescription("");

        // Customize the X and Y axes
        XAxis xAxis = b.lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new MonthValueFormatter());
//        xAxis.setValueFormatter((value, axis) -> {
//            getMonthLabel((int) value);
//        });

        YAxis leftAxis = b.lineChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f); // Start at 0

        YAxis rightAxis = b.lineChart.getAxisRight();
        rightAxis.setEnabled(false); // Disable the right Y-axis

        // Create a data object with the dataset
        LineData lineData = new LineData(lineDataSet);

        // Set data to the chart
        b.lineChart.setData(lineData);
        b.lineChart.invalidate(); // Refresh chart



        // bar chart
        // Initialize b.barChart
        // Configure the bar chart appearance
        configureChartAppearance();

        // Set chart data
        setChartData();

        //pie chart
        configureChartAppearance1();

        // Set chart data
        setChartData1();

    }
    private void configureChartAppearance() {
        // Customize the X-Axis
        XAxis xAxis = b.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new DayValueFormatter());

        // Customize the Y-Axis
        YAxis leftAxis = b.barChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        b.barChart.getAxisRight().setEnabled(false); // Disable right Y-axis

        // Enable value above each bar
        b.barChart.getDescription().setEnabled(false); // Disable description label
        b.barChart.setDrawValueAboveBar(true);
    }

    private void setChartData() {
        // Prepare the data entries
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 8)); // Monday
        entries.add(new BarEntry(1f, 10)); // Tuesday
        entries.add(new BarEntry(2f, 14)); // Wednesday
        entries.add(new BarEntry(3f, 15)); // Thursday
        entries.add(new BarEntry(4f, 13)); // Friday
        entries.add(new BarEntry(5f, 10)); // Saturday
        entries.add(new BarEntry(6f, 16)); // Sunday

        // Create a BarDataSet with gradient fill
        BarDataSet dataSet = new BarDataSet(entries, "Week Data");

        // Set gradient color
        dataSet.setGradientColor(Color.parseColor("#4CAF50"), Color.parseColor("#00FF00")); // Start and end colors

        // Customize the dataset appearance
        dataSet.setValueTextSize(14f); // Size of value text
        dataSet.setValueTextColor(ColorTemplate.getHoloBlue()); // Value text color

        // Create BarData
        BarData barData = new BarData(dataSet);
        b.barChart.setData(barData);
        b.barChart.invalidate(); // Refresh chart
    }

    class MonthValueFormatter extends ValueFormatter {

        private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public String getFormattedValue(float value) {
            // Ensure the value is within the bounds of the array
            int index = (int) value;
            if (index >= 0 && index < months.length) {
                return months[index];
            } else {
                return ""; // Return empty string if out of bounds
            }
        }
    }


   class DayValueFormatter extends ValueFormatter {

        private final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index >= 0 && index < days.length) {
                return days[index];
            } else {
                return ""; // Handle out of bounds
            }
        }
    }


    private void configureChartAppearance1() {
        b.donutChart.setUsePercentValues(true);
        b.donutChart.getDescription().setEnabled(false);
        b.donutChart.setHoleRadius(60f); // Inner radius of the donut
        b.donutChart.setTransparentCircleRadius(65f); // Transparent circle
        b.donutChart.setEntryLabelTextSize(14f);
        b.donutChart.setDrawEntryLabels(false); // Disable entry labels on slices

        // Enable legend and its style
        b.donutChart.getLegend().setEnabled(true);
        b.donutChart.getLegend().setTextSize(14f);
        b.donutChart.getLegend().setFormSize(14f);
        b.donutChart.getLegend().setFormToTextSpace(10f);
    }

    private void setChartData1() {
        // Create entries for the PieChart
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "First"));
        entries.add(new PieEntry(30f, "Second"));
        entries.add(new PieEntry(15f, "Third"));
        entries.add(new PieEntry(15f, "Fourth"));

        // Set colors for the slices
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(127, 0, 255)); // Purple color
        colors.add(Color.rgb(0, 204, 102)); // Green color
        colors.add(Color.rgb(0, 102, 51));  // Dark Green color
        colors.add(Color.rgb(255, 153, 51)); // Orange color

        // Create a PieDataSet with gradient fill
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors); // Set the colors

        // Customize dataSet properties
        dataSet.setSliceSpace(3f); // Space between slices
        dataSet.setValueTextSize(18f); // Size of value text
        dataSet.setValueTextColor(Color.WHITE); // Value text color

        // Create PieData
        PieData pieData = new PieData(dataSet);
        b.donutChart.setData(pieData);
        b.donutChart.invalidate(); // Refresh chart
    }

}
